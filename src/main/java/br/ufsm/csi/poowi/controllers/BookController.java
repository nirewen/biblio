package br.ufsm.csi.poowi.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import br.ufsm.csi.poowi.dao.BookDAO;
import br.ufsm.csi.poowi.model.Book;
import br.ufsm.csi.poowi.model.User;
import br.ufsm.csi.poowi.util.UserException;
import br.ufsm.csi.poowi.util.UserException.Type;

@WebServlet("/book")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class BookController extends HttpServlet {
    private final BookDAO dao = new BookDAO();
    private final ArrayList<String> VALID_EXTENSIONS = new ArrayList<String>() {
        {
            add("jpg");
            add("jpeg");
            add("png");
            add("webp");
        }
    };

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getParameter("option");
        String id = req.getParameter("id");

        String route = "/WEB-INF/views/book.jsp";

        if (option != null && !option.isEmpty()) {
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");

            if (user == null) {
                session.setAttribute("error", new UserException(Type.LOGGED_OUT, "Não logado"));

                String redirectTo = "/book?";

                if (id != null && !id.isEmpty())
                    redirectTo += "id=" + id + "&";

                redirectTo += "option=" + option;

                session.setAttribute("redirectTo", redirectTo);

                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }

            if (option.equals("edit")) {
                route = "/WEB-INF/views/edit_book.jsp";

                if (id == null || id.isEmpty()) {
                    // redirectionar para a lista caso id ausente
                    resp.sendRedirect(req.getContextPath() + "/books");

                    return;
                }
            }

            if (option.equals("new"))
                route = "/WEB-INF/views/new_book.jsp";
        } else if (id == null || id.isEmpty()) {
            // redirectionar para a lista caso id ausente
            resp.sendRedirect(req.getContextPath() + "/books");

            return;
        } else {
            Book book = dao.getBook(Integer.parseInt(id));

            if (book == null) {
                resp.sendRedirect(req.getContextPath() + "/books");
                return;
            }

            req.setAttribute("book", book);
        }

        RequestDispatcher rd = req.getRequestDispatcher(route);

        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        String option = req.getParameter("option");

        if (user == null) {
            session.setAttribute("error", new UserException(Type.LOGGED_OUT, "Não logado"));

            String redirectTo = "/book";

            if (option != null)
                redirectTo += "?option=" + option;

            session.setAttribute("redirectTo", redirectTo);

            resp.sendRedirect(req.getContextPath() + "/login");

            return;
        }

        String name = req.getParameter("name");
        String synopsis = req.getParameter("synopsis");
        int pages = Integer.parseInt(StringUtils.defaultIfEmpty(req.getParameter("pages"), "0"));
        float chapters = Float.parseFloat(StringUtils.defaultIfEmpty(req.getParameter("chapters"), "0"));
        String author = req.getParameter("author");
        String publisher = req.getParameter("publisher");
        int year = Integer.parseInt(
                StringUtils.defaultIfEmpty(req.getParameter("year"),
                        Integer.toString(Calendar.getInstance().get(Calendar.YEAR))));
        Part coverFile = req.getPart("cover");

        InputStream coverIS = coverFile.getInputStream();
        byte[] imageBytes = new byte[(int) coverFile.getSize()];
        coverIS.read(imageBytes, 0, imageBytes.length);
        coverIS.close();

        String fileName = coverFile.getSubmittedFileName();
        String ext = fileName.substring(fileName.lastIndexOf('.') + 1);

        if (!VALID_EXTENSIONS.contains(ext)) {
            req.setAttribute("error", "Tipo de arquivo para capa inválido!");
        } else {
            String cover = "data:image/" + ext + ";base64," + Base64.encodeBase64String(imageBytes);

            if (option.equals("new")) {
                Book book = new Book();

                book.setName(name);
                book.setSynopsis(synopsis);
                book.setPages(pages);
                book.setChapters(chapters);
                book.setAuthor(author);
                book.setPublisher(publisher);
                book.setYear(year);
                book.setCover(cover);

                boolean success = dao.createBook(book);

                if (success) {
                    req.setAttribute("message", "Livro criado com sucesso!");

                    resp.sendRedirect(req.getContextPath() + "/books");

                    return;
                }
            }
        }

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/book.jsp");

        rd.forward(req, resp);
    }
}

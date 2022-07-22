package br.ufsm.csi.poowi.controllers;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ufsm.csi.poowi.dao.UserDAO;
import br.ufsm.csi.poowi.model.User;
import br.ufsm.csi.poowi.service.UserService;
import br.ufsm.csi.poowi.util.UserException;
import br.ufsm.csi.poowi.util.UserException.Type;

@Controller
@RequestMapping("/login")
public class LoginController extends HttpServlet {
    @GetMapping
    protected String loginPage(HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user != null) {
            return "redirect:/dashboard";
        }

        session.removeAttribute("error");

        return "login";
    }

    @PostMapping
    protected String login(HttpSession session, Model model, @ModelAttribute User loginInfo) {
        String redirectTo = StringUtils.defaultIfEmpty((String) session.getAttribute("redirectTo"), "/dashboard");

        UserDAO dao = new UserDAO();
        UserService us = new UserService();

        User user = dao.getUser(loginInfo.getEmail());

        if (us.autenticado(user, loginInfo.getPassword())) {
            session.setAttribute("user", user);

            return "redirect:" + redirectTo;
        } else {
            UserException error = new UserException(Type.INCORRECT_CREDENTIALS, "Email ou senha incorretos");

            model.addAttribute("error", error);
            model.addAttribute("email", loginInfo.getEmail());
        }

        if (model.getAttribute("error") == null)
            session.removeAttribute("redirectTo");

        return "login";
    }
}

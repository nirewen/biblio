package br.ufsm.csi.poowi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.ufsm.csi.poowi.model.Book;
import br.ufsm.csi.poowi.util.DBConnect;

public class BookDAO {
    public int nextId() {
        int last_value = -1;

        try (Connection con = new DBConnect().getConnection()) {
            String sql = "SELECT last_value FROM books_id_seq;";

            Statement statement = con.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next())
                last_value = resultSet.getInt("last_value");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return last_value + 1;
    }

    private Book fromResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();

        book.setId(resultSet.getInt("id"));
        book.setName(resultSet.getString("name"));
        book.setSynopsis(resultSet.getString("synopsis"));
        book.setPages(resultSet.getInt("pages"));
        book.setChapters(resultSet.getFloat("chapters"));
        book.setAuthor(resultSet.getString("author"));
        book.setPublisher(resultSet.getString("publisher"));
        book.setYear(resultSet.getInt("year"));
        book.setCover(resultSet.getString("cover"));

        return book;
    }

    public Book getBook(int id) {
        Book book = null;

        try (Connection con = new DBConnect().getConnection()) {
            String sql = "SELECT * FROM books WHERE id = ?;";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                book = this.fromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return book;
    }

    public boolean createBook(Book book) {
        try (Connection con = new DBConnect().getConnection()) {
            // possível bug: registrar o mesmo livro
            // (pode existir dois autores que fizeram um livro com mesmo nome)
            String sql = "INSERT INTO books (name, synopsis, pages, chapters, author, publisher, year, cover) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, book.getName());
            preparedStatement.setString(2, book.getSynopsis());
            preparedStatement.setInt(3, book.getPages());
            preparedStatement.setFloat(4, book.getChapters());
            preparedStatement.setString(5, book.getAuthor());
            preparedStatement.setString(6, book.getPublisher());
            preparedStatement.setInt(7, book.getYear());
            preparedStatement.setString(8, book.getCover());

            preparedStatement.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateBook(int id, Book newBook) {
        try (Connection con = new DBConnect().getConnection()) {
            String sql = "UPDATE books SET name = ?, synopsis = ?, pages = ?, chapters = ?, author = ?, publisher = ?, year = ? WHERE id = ?";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, newBook.getName());
            preparedStatement.setString(2, newBook.getSynopsis());
            preparedStatement.setInt(3, newBook.getPages());
            preparedStatement.setFloat(4, newBook.getChapters());
            preparedStatement.setString(5, newBook.getAuthor());
            preparedStatement.setString(6, newBook.getPublisher());
            preparedStatement.setInt(7, newBook.getYear());
            preparedStatement.setInt(8, id);

            preparedStatement.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Book> getBookList() {
        ArrayList<Book> books = new ArrayList<>();

        try (Connection con = new DBConnect().getConnection()) {
            String sql = "SELECT * FROM books;";

            PreparedStatement preparedStatement = con.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                books.add(this.fromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }
}

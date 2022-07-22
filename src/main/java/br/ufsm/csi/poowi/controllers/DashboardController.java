package br.ufsm.csi.poowi.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ufsm.csi.poowi.dao.LoanDAO;
import br.ufsm.csi.poowi.model.User;
import br.ufsm.csi.poowi.util.UserException;
import br.ufsm.csi.poowi.util.UserException.Type;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    private final LoanDAO loanDAO = new LoanDAO();

    @GetMapping
    protected String dashboardPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            session.setAttribute("error", new UserException(Type.LOGGED_OUT, "Não logado"));
            session.setAttribute("redirectTo", "/dashboard");

            return "redirect:/login";
        }

        model.addAttribute("loans", loanDAO.getLoans(user.getId()));

        return "dashboard";
    }
}

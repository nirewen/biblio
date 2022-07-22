package br.ufsm.csi.poowi.controllers;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ufsm.csi.poowi.model.User;

@Controller
@RequestMapping("/logout")
public class LogoutController extends HttpServlet {

    @GetMapping
    public String logoutPage(HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user != null) {
            session.invalidate();
        }

        return "redirect:/";
    }
}

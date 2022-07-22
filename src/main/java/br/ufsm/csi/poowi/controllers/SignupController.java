package br.ufsm.csi.poowi.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ufsm.csi.poowi.dao.UserDAO;
import br.ufsm.csi.poowi.model.User;
import br.ufsm.csi.poowi.util.UserException;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private final UserDAO dao = new UserDAO();

    @GetMapping
    protected String signUpPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        if (user != null) {
            return "redirect:/dashboard";
        }

        model.addAttribute("newUser", new User());

        return "signup";
    }

    @PostMapping
    protected String signUp(Model model, @ModelAttribute("newUser") User user) {
        try {
            boolean success = dao.createUser(user.getEmail(), user.getPassword());

            if (success) {
                // TODO: session!
                model.addAttribute("message", "Usuário criado com sucesso!");

                return "redirect:/login";
            }
        } catch (UserException e) {
            model.addAttribute("error", e);
        }

        return "signup";
    }
}

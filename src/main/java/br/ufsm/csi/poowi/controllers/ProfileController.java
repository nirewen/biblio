package br.ufsm.csi.poowi.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ufsm.csi.poowi.model.User;
import br.ufsm.csi.poowi.service.UserService;
import br.ufsm.csi.poowi.util.EditedUser;
import br.ufsm.csi.poowi.util.UserException;
import br.ufsm.csi.poowi.util.UserException.Type;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    protected String profilePage(HttpSession session) {
        User user = (User) session.getAttribute("$user");

        if (user == null) {
            session.setAttribute("$error", new UserException(Type.LOGGED_OUT, "Não logado"));

            session.setAttribute("$redirectTo", "/profile");

            return "redirect:/login";
        }

        session.removeAttribute("message");

        return "profile";
    }

    @GetMapping("/edit")
    protected String editUserPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("$user");

        if (user == null) {
            session.setAttribute("$error", new UserException(Type.LOGGED_OUT, "Não logado"));

            session.setAttribute("$redirectTo", "/profile/edit");

            return "redirect:/login";
        }

        EditedUser editedUser = new EditedUser(user);

        model.addAttribute("user", editedUser);

        return "edit_profile";
    }

    @PostMapping("/edit")
    protected String editUser(HttpSession session, Model model, @ModelAttribute("user") EditedUser newUser) {
        User user = (User) session.getAttribute("$user");

        if (user == null) {
            session.setAttribute("$error", new UserException(Type.LOGGED_OUT, "Não logado"));

            session.setAttribute("$redirectTo", "/profile");

            return "redirect:/login";
        }

        if (!user.getPassword().equals(newUser.getPassword())) {
            model.addAttribute("error", new UserException(Type.INCORRECT_CREDENTIALS,
                    "Senha atual incorreta"));

            return "profile";
        }

        newUser.setPassword(newUser.getNewPassword().isEmpty() ? newUser.getPassword() : newUser.getNewPassword());

        try {
            this.userService.updateUser(newUser);

            session.setAttribute("$user", newUser);
            session.setAttribute("$message", "Usuário atualizado");

            return "redirect:/profile";
        } catch (UserException e) {
            model.addAttribute("error", e);
        }

        return "redirect:/profile";
    }
}

package br.ufsm.csi.poowi.service;

import br.ufsm.csi.poowi.dao.UserDAO;
import br.ufsm.csi.poowi.model.User;

public class UserService {
    private final UserDAO dao = new UserDAO();

    public User autenticado(String email, String password) {
        User u = dao.getUser(email);

        if (u != null && u.getPassword().equals(password)) {
            return u;
        }

        return null;
    }
}

package br.ufsm.csi.poowi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufsm.csi.poowi.dao.UserDAO;
import br.ufsm.csi.poowi.model.User;
import br.ufsm.csi.poowi.util.UserException;

@Service
public class UserService {
    @Autowired
    private UserDAO dao;

    public boolean isAuthenticated(User user, String password) {
        if (user == null)
            return false;

        if (user.getPassword().equals(password)) {
            return true;
        }

        return false;
    }

    public User getUser(int userId) {
        return this.dao.getUser(userId);
    }

    public User getUserByEmail(String email) {
        return this.dao.getUser(email);
    }

    public boolean createUser(String email, String password) throws UserException {
        return this.dao.createUser(email, password);
    }

    public boolean updateUser(User newUser) throws UserException {
        return this.dao.updateUser(newUser);
    }
}

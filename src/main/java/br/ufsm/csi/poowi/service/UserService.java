package br.ufsm.csi.poowi.service;

import br.ufsm.csi.poowi.model.User;

public class UserService {
    public boolean autenticado(User user, String password) {
        if (user == null) return false;
        
        if (user.getPassword().equals(password)) {
            return true;
        }

        return false;
    }
}

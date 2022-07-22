package br.ufsm.csi.poowi.util;

import br.ufsm.csi.poowi.model.User;

public class EditedUser extends User {
    public EditedUser() {
    }

    public EditedUser(User user) {
        this.setId(user.getId());
        this.setName(user.getName());
        this.setEmail(user.getEmail());
        this.setDateOfBirth(user.getDateOfBirth());
        this.setPermission(user.getPermission());
    }

    private String newPassword = "";

    public String getNewPassword() {
        return this.newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
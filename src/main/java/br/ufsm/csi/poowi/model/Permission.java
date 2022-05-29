package br.ufsm.csi.poowi.model;

public enum Permission {
    ADMIN(8),
    USER(16);

    private int permission = 0;

    private Permission(int value) {
        this.permission = value;
    }

    public static boolean isPermitted(int totalPermissions, Permission compare) {
        return (totalPermissions & compare.permission) == compare.permission;
    }
}

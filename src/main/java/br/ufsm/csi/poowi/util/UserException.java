package br.ufsm.csi.poowi.util;

public class UserException extends Throwable {
    public static enum Type {
        DUPLICATE_USER,
        INCORRECT_CREDENTIALS,
        LOGGED_OUT
    }

    public final Type type;
    public final String message;

    public UserException(Type type, String message) {
        this.type = type;
        this.message = message;
    }

    public Type getType() {
        return this.type;
    }

    public String getMessage() {
        return this.message;
    }
}

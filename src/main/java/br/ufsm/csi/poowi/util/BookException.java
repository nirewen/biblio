package br.ufsm.csi.poowi.util;

public class BookException {
    public static enum Type {
        INVALID_COVER_TYPE
    }

    public final Type type;
    public final String message;

    public BookException(Type type, String message) {
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

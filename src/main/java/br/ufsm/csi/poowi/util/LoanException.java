package br.ufsm.csi.poowi.util;

public class LoanException {
    public static enum Type {
        BOOK_ALREADY_LOANED
    }

    public final Type type;
    public final String message;

    public LoanException(Type type, String message) {
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

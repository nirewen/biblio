package br.ufsm.csi.poowi.util;

public class RentalException {
    public static enum Type {
        BOOK_ALREADY_RENTED
    }

    public final Type type;
    public final String message;

    public RentalException(Type type, String message) {
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

package fr.pinguet62.jsfring.gui.htmlunit;

public final class NavigatorException extends RuntimeException {

    private static final long serialVersionUID = 1;

    public NavigatorException() {
    }

    public NavigatorException(String message) {
        super(message);
    }

    public NavigatorException(Throwable cause) {
        super(cause);
    }

}
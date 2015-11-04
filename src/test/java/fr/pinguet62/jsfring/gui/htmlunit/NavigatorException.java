package fr.pinguet62.jsfring.gui.htmlunit;

public final class NavigatorException extends RuntimeException {

    private static final long serialVersionUID = 1;

    /** Default constructor. */
    public NavigatorException() {}

    /**
     * Constructor with message.
     *
     * @param message The {@link Exception#getMessage()}.
     */
    public NavigatorException(String message) {
        super(message);
    }

    /**
     * Constructor with cause.
     *
     * @param cause The {@link Exception#getCause()}.
     */
    public NavigatorException(Throwable cause) {
        super(cause);
    }

}
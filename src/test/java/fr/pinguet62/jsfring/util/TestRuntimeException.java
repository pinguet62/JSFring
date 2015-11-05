package fr.pinguet62.jsfring.util;

public class TestRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1;

    /** Default constructor. */
    public TestRuntimeException() {}

    /**
     * Constructor with cause.
     *
     * @param cause The {@link Exception#getCause()}.
     */
    public TestRuntimeException(Throwable cause) {
        super(cause);
    }

}
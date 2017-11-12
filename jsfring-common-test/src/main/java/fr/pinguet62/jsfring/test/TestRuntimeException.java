package fr.pinguet62.jsfring.test;

/**
 * Simple override of {@link RuntimeException} used by tests.
 */
public class TestRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1;

    /**
     * Default constructor.
     *
     * @see RuntimeException#RuntimeException()
     */
    public TestRuntimeException() {
    }

    /**
     * Constructor with message.
     *
     * @param message The detail message.
     * @see RuntimeException#RuntimeException(String)
     */
    public TestRuntimeException(String message) {
        super(message);
    }

    /**
     * Constructor with cause.
     *
     * @param cause The {@link Exception#getCause()}.
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public TestRuntimeException(Throwable cause) {
        super(cause);
    }

}
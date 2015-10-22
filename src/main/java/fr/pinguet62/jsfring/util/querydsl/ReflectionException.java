package fr.pinguet62.jsfring.util.querydsl;

/**
 * {@link Exception} thrown when an problem occurs during reflection
 * reading/writing values.
 */
public final class ReflectionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ReflectionException(String message, Throwable cause) {
        super(message, cause);
    }

}
package bftsmart.microbenchmark.tpcc.exception;

/**
 * Signals that something could not be found.
 */
public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = -2515448000831646951L;

    /**
     * Construct an exception with a message.
     *
     * @param message
     *            The reason for the exception
     */
    public NotFoundException(String message) {
        super(message);
    }

    /**
     * Construct an exception with a message and arguments.
     *
     * @param message
     *            A format string for the exception
     * @param args
     *            Arguments referenced by the format specifiers in the format
     *            string.
     */
    public NotFoundException(String message, Object... args) {
        super(String.format(message, args));
    }

    /**
     * Construct an exception with a message.
     *
     * @param message
     *            The reason for the exception
     * @param cause
     *            The cause
     */
    public NotFoundException(String message, Exception cause) {
        super(message + " because of " + cause.toString(), cause);
    }

}
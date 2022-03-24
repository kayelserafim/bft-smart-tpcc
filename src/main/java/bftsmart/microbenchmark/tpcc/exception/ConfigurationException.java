package bftsmart.microbenchmark.tpcc.exception;

/**
 * This exception is thrown when an error occurs reading from, parsing, using, or initializing the properties file
 * configuration.
 */
public class ConfigurationException extends RuntimeException {

    private static final long serialVersionUID = -2413951820300775294L;

    /**
     * Construct an exception with a message.
     *
     * @param message
     *            The reason for the exception
     */
    public ConfigurationException(final String message) {
        super(message);
    }

    /**
     * Constructs a new configuration exception with the specified cause and a
     * detail message of <tt>(cause==null ? null : cause.toString())</tt>
     * (which typically contains the class and detail message of
     * <tt>cause</tt>). This constructor is useful for configuration exceptions
     * that are little more than wrappers for other throwables.
     *
     * @param cause
     *            the cause (which is saved for later retrieval by the
     *            {@link #getCause()} method). (A <tt>null</tt> value is
     *            permitted, and indicates that the cause is nonexistent or
     *            unknown.)
     */
    public ConfigurationException(Throwable cause) {
        super(cause);
    }

}
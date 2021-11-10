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
     * Construct an exception with an underlying cause.
     *
     * @param cause
     *            The underlying cause of the exception
     */
    public ConfigurationException(final Throwable cause) {
        super(cause);
    }

    /**
     * Construct an exception with a message and underlying cause.
     *
     * @param message
     *            The reason for the exception
     * @param cause
     *            The underlying cause of the exception
     */
    public ConfigurationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
package com.angelis.tera.common.config.exception;

public class TransformationException extends RuntimeException {
    /**
     * SerialID
     */
    private static final long serialVersionUID = -6641235751743285902L;

    /**
     * Creates new instance of exception
     */
    public TransformationException() {}

    /**
     * Creates new instance of exception
     *
     * @param message exception message
     */
    public TransformationException(String message) {
        super(message);
    }

    /**
     * Creates new instance of exception
     *
     * @param message exception message
     * @param cause   exception that is the reason of this exception
     */
    public TransformationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates new instance of exception
     *
     * @param cause exception that is the reason of this exception
     */
    public TransformationException(Throwable cause) {
        super(cause);
    }
}

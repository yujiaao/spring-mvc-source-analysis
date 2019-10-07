package ${package}.${artifactId}.exceptions;

/**
 * This exception should be thrown in all cases when a resource cannot be found
 *
 * @author xwx
 */
public class NotAllowedOperationException extends RuntimeException {

    /**
     *
     * @param message the message
     */
    public NotAllowedOperationException(final String message) {
        super(message);
    }
}

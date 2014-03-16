package com.goproject100.demo.model;

/**
 * This object represents the server response when new data has been posted.
 */
public class DataPacketResponse {

    private final Status status;
    private final Exception exception;

    /**
     * This private constructor forces the factory pattern on this object. Users must either create a successful
     * or failure response via the static factory methods.
     * @param status the {@link Status} of this response
     * @param exception any {@link Exception} that occurred in receiving the new data
     */
    private DataPacketResponse(final Status status, final Exception exception) {
        this.status = status;
        this.exception = exception;
    }

    /**
     * @return a generated successful response.
     */
    public static DataPacketResponse newSuccessResponse() {
        return new DataPacketResponse(Status.SUCCESS, null);
    }

    /**
     * @param exception the exception that caused the failure
     * @return a generated failure response
     */
    public static DataPacketResponse newFailureResponse(final Exception exception) {
        return new DataPacketResponse(Status.FAILURE, exception);
    }

    /**
     * @return the status of this response.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @return the exception (if any) generated while receiving the new data
     */
    public Exception getException() {
        return exception;
    }

    /**
     * This enum describes the statuses available in the response.
     */
    public enum Status {
        SUCCESS,
        FAILURE
    }
}

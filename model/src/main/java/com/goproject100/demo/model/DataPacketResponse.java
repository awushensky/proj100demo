package com.goproject100.demo.model;

/**
 * This object represents the server response.
 */
public class DataPacketResponse {

    private final Status status;
    private final Object responseBody;
    private final Exception exception;

    /**
     * This private constructor forces the factory pattern on this object. Users must either create a successful
     * or failure response via the static factory methods.
     * @param status the {@link Status} of this response
     * @param responseBody the response for the call
     * @param exception any {@link Exception} that occurred in receiving the new data
     */
    private DataPacketResponse(final Status status, final Object responseBody, final Exception exception) {
        this.status = status;
        this.responseBody = responseBody;
        this.exception = exception;
    }

    /**
     * @return a generated successful response.
     */
    public static DataPacketResponse newSuccessResponse() {
        return new DataPacketResponse(Status.SUCCESS, null, null);
    }

    /**
     * @param responseBody the body to include in the response
     * @return a generated successful response.
     */
    public static DataPacketResponse newSuccessResponse(final Object responseBody) {
        return new DataPacketResponse(Status.SUCCESS, responseBody, null);
    }

    /**
     * @param exception the exception that caused the failure
     * @return a generated failure response
     */
    public static DataPacketResponse newFailureResponse(final Exception exception) {
        return new DataPacketResponse(Status.FAILURE, null, exception);
    }

    /**
     * @return the status of this response.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @return the exception (if any)
     */
    public Exception getException() {
        return exception;
    }

    /**
     * @return the response body (if any) included in this response
     */
    public Object getResponseBody() {
        return responseBody;
    }

    /**
     * This enum describes the statuses available in the response.
     */
    public enum Status {
        SUCCESS,
        FAILURE
    }
}

package in.gskitchen.indusnet.exceptions;

import java.util.Date;

/**
 * This class made for Custom Spring boot error response.
 */
public class ExepceptionResponse {
    private Date timestamp;
    private String message, details;

    /**
     * This constructor take three parameter as this.
     * @param timestamp as {@link Date}
     * @param message as String
     * @param details as String
     */
    public ExepceptionResponse(Date timestamp, String message, String details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}

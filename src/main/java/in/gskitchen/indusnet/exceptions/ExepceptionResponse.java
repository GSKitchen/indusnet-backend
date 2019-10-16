package in.gskitchen.indusnet.exceptions;

import java.util.Date;

public class ExepceptionResponse {
    private Date timestamp;
    private String message, details;

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

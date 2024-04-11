package dev.bananaftmeo.netcafeserver.models.responses;

public class ImageDeleteResponse {
    private String message;

    public ImageDeleteResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

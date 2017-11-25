package ua.meugen.android.popularmovies.model.network.resp;

import com.google.gson.annotations.SerializedName;

/**
 * @author meugen
 */

public class BaseResponse {

    @SerializedName("status_message")
    private String statusMessage = "";
    @SerializedName("status_code")
    private int statusCode = 0;
    @SerializedName("success")
    private boolean success = true;

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(final String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(final int statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(final boolean success) {
        this.success = success;
    }
}

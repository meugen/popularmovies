package ua.meugen.android.popularmovies.model.network.resp;

import com.google.gson.annotations.SerializedName;

/**
 * @author meugen
 */

public class NewSessionResponse extends BaseResponse {

    @SerializedName("session_id")
    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(final String sessionId) {
        this.sessionId = sessionId;
    }
}
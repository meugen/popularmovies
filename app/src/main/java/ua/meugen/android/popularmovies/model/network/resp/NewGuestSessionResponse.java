package ua.meugen.android.popularmovies.model.network.resp;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import ua.meugen.android.popularmovies.model.network.adapters.DateTimeTypeAdapter;

/**
 * @author meugen
 */

public class NewGuestSessionResponse extends BaseResponse {

    @SerializedName("guest_session_id")
    private String guestSessionId;
    @SerializedName("expires_at")
    @JsonAdapter(DateTimeTypeAdapter.class)
    private Date expiresAt;

    public String getGuestSessionId() {
        return guestSessionId;
    }

    public void setGuestSessionId(final String guestSessionId) {
        this.guestSessionId = guestSessionId;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(final Date expiresAt) {
        this.expiresAt = expiresAt;
    }
}
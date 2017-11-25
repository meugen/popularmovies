package ua.meugen.android.popularmovies.model.network.resp;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import ua.meugen.android.popularmovies.model.network.adapters.DateTimeTypeAdapter;

/**
 * @author meugen
 */

public class NewTokenResponse extends BaseResponse {

    @SerializedName("expires_at")
    @JsonAdapter(DateTimeTypeAdapter.class)
    private Date expiresAt;
    @SerializedName("request_token")
    private String token;

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(final Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }
}
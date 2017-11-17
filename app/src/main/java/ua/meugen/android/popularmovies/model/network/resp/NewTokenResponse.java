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
    public Date expiresAt;
    @SerializedName("request_token")
    public String token;

//    @Override
//    public boolean equals(final Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        if (!super.equals(o)) return false;
//
//        final NewTokenResponse that = (NewTokenResponse) o;
//
//        if (expiresAt != null ? !expiresAt.equals(that.expiresAt) : that.expiresAt != null)
//            return false;
//        return token != null ? token.equals(that.token) : that.token == null;
//
//    }
//
//    @Override
//    public int hashCode() {
//        int result = super.hashCode();
//        result = 31 * result + (expiresAt != null ? expiresAt.hashCode() : 0);
//        result = 31 * result + (token != null ? token.hashCode() : 0);
//        return result;
//    }
}
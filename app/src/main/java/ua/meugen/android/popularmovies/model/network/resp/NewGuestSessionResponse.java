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
    public String guestSessionId;
    @SerializedName("expires_at")
    @JsonAdapter(DateTimeTypeAdapter.class)
    public Date expiresAt;

//    @Override
//    public boolean equals(final Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        if (!super.equals(o)) return false;
//
//        final NewGuestSessionResponse that = (NewGuestSessionResponse) o;
//
//        if (guestSessionId != null ? !guestSessionId.equals(that.guestSessionId) : that.guestSessionId != null)
//            return false;
//        return expiresAt != null ? expiresAt.equals(that.expiresAt) : that.expiresAt == null;
//
//    }
//
//    @Override
//    public int hashCode() {
//        int result = super.hashCode();
//        result = 31 * result + (guestSessionId != null ? guestSessionId.hashCode() : 0);
//        result = 31 * result + (expiresAt != null ? expiresAt.hashCode() : 0);
//        return result;
//    }
}
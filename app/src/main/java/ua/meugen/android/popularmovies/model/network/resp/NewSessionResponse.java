package ua.meugen.android.popularmovies.model.network.resp;

import com.google.gson.annotations.SerializedName;

/**
 * @author meugen
 */

public class NewSessionResponse extends BaseResponse {

    @SerializedName("session_id")
    public String sessionId;

//    @Override
//    public boolean equals(final Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        if (!super.equals(o)) return false;
//
//        final NewSessionResponse that = (NewSessionResponse) o;
//
//        return sessionId != null ? sessionId.equals(that.sessionId) : that.sessionId == null;
//
//    }
//
//    @Override
//    public int hashCode() {
//        int result = super.hashCode();
//        result = 31 * result + (sessionId != null ? sessionId.hashCode() : 0);
//        return result;
//    }
}
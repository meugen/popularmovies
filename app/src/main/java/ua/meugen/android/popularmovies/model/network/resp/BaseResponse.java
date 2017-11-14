package ua.meugen.android.popularmovies.model.network.resp;

import com.google.gson.annotations.SerializedName;

/**
 * @author meugen
 */

public class BaseResponse {

    @SerializedName("status_message")
    public String statusMessage = "";
    @SerializedName("status_code")
    public int statusCode = 0;
    @SerializedName("success")
    public boolean success = true;

//    @Override
//    public boolean equals(final Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        final BaseResponse that = (BaseResponse) o;
//
//        if (statusCode != that.statusCode) return false;
//        if (success != that.success) return false;
//        return statusMessage != null ? statusMessage.equals(that.statusMessage) : that.statusMessage == null;
//
//    }
//
//    @Override
//    public int hashCode() {
//        int result = statusMessage != null ? statusMessage.hashCode() : 0;
//        result = 31 * result + statusCode;
//        result = 31 * result + (success ? 1 : 0);
//        return result;
//    }
}

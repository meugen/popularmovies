package ua.meugen.android.popularmovies.model.responses;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author meugen
 */

public class NewSessionDto extends BaseResponse implements Parcelable {

    public static final Creator<NewSessionDto> CREATOR
            = new NewSessionDtoCreator();

    @SerializedName("session_id")
    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(final String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int flags) {
        _writeToParcel(parcel);
        parcel.writeString(sessionId);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        final NewSessionDto that = (NewSessionDto) o;

        return sessionId != null ? sessionId.equals(that.sessionId) : that.sessionId == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (sessionId != null ? sessionId.hashCode() : 0);
        return result;
    }
}

class NewSessionDtoCreator implements Parcelable.Creator<NewSessionDto> {

    @Override
    public NewSessionDto createFromParcel(final Parcel parcel) {
        final NewSessionDto dto = new NewSessionDto();
        dto._readFromParcel(parcel);
        dto.setSessionId(parcel.readString());
        return dto;
    }

    @Override
    public NewSessionDto[] newArray(final int size) {
        return new NewSessionDto[size];
    }
}
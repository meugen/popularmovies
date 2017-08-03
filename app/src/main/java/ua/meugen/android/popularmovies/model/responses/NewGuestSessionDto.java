package ua.meugen.android.popularmovies.model.responses;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import ua.meugen.android.popularmovies.model.typeadapters.DateTimeTypeAdapter;

/**
 * @author meugen
 */

public class NewGuestSessionDto extends BaseResponse implements Parcelable {

    public static final Creator<NewGuestSessionDto> CREATOR
            = new NewGuestSessionDtoCreator();

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int flags) {
        _writeToParcel(parcel);
        parcel.writeString(guestSessionId);
        parcel.writeLong(expiresAt.getTime());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        final NewGuestSessionDto that = (NewGuestSessionDto) o;

        if (guestSessionId != null ? !guestSessionId.equals(that.guestSessionId) : that.guestSessionId != null)
            return false;
        return expiresAt != null ? expiresAt.equals(that.expiresAt) : that.expiresAt == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (guestSessionId != null ? guestSessionId.hashCode() : 0);
        result = 31 * result + (expiresAt != null ? expiresAt.hashCode() : 0);
        return result;
    }
}

class NewGuestSessionDtoCreator implements Parcelable.Creator<NewGuestSessionDto> {

    @Override
    public NewGuestSessionDto createFromParcel(final Parcel parcel) {
        final NewGuestSessionDto dto = new NewGuestSessionDto();
        dto._writeToParcel(parcel);
        dto.setGuestSessionId(parcel.readString());
        dto.setExpiresAt(new Date(parcel.readLong()));
        return dto;
    }

    @Override
    public NewGuestSessionDto[] newArray(final int size) {
        return new NewGuestSessionDto[size];
    }
}
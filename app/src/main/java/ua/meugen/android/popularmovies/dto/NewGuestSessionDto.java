package ua.meugen.android.popularmovies.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * @author meugen
 */

public class NewGuestSessionDto extends BaseResponse implements Parcelable {

    public static final Creator<NewGuestSessionDto> CREATOR
            = new NewGuestSessionDtoCreator();

    private boolean success;
    private String guestSessionId;
    private Date expiresAt;

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public void setSuccess(final boolean success) {
        this.success = success;
    }

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
        parcel.writeBooleanArray(new boolean[] { success });
        parcel.writeString(guestSessionId);
        parcel.writeLong(expiresAt.getTime());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        final NewGuestSessionDto that = (NewGuestSessionDto) o;

        if (success != that.success) return false;
        if (guestSessionId != null ? !guestSessionId.equals(that.guestSessionId) : that.guestSessionId != null)
            return false;
        return expiresAt != null ? expiresAt.equals(that.expiresAt) : that.expiresAt == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (success ? 1 : 0);
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
        final boolean[] bools = new boolean[1];
        parcel.readBooleanArray(bools);
        dto.setSuccess(bools[0]);
        dto.setGuestSessionId(parcel.readString());
        dto.setExpiresAt(new Date(parcel.readLong()));
        return dto;
    }

    @Override
    public NewGuestSessionDto[] newArray(final int size) {
        return new NewGuestSessionDto[size];
    }
}
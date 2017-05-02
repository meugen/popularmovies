package ua.meugen.android.popularmovies.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.JsonReader;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ua.meugen.android.popularmovies.json.JsonReadable;

/**
 * @author meugen
 */

public class NewGuestSessionDto extends BaseResponse implements Parcelable {

    public static final Creator<NewGuestSessionDto> CREATOR
            = new NewGuestSessionDtoCreator();
    public static final JsonReadable<NewGuestSessionDto> READABLE
            = new NewGuestSessionDtoReadable();

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

class NewGuestSessionDtoReadable implements JsonReadable<NewGuestSessionDto> {

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss zzz", Locale.ENGLISH);

    @Override
    public NewGuestSessionDto readJson(final JsonReader reader) throws IOException {
        final NewGuestSessionDto dto = new NewGuestSessionDto();

        reader.beginObject();
        while (reader.hasNext()) {
            final String name = reader.nextName();
            if ("success".equals(name)) {
                dto.setSuccess(reader.nextBoolean());
            } else if ("guest_session_id".equals(name)) {
                dto.setGuestSessionId(reader.nextString());
            } else if ("expires_at".equals(name)) {
                dto.setExpiresAt(nextDate(reader));
            } else {
                dto._readFromJson(reader, name);
            }
        }
        reader.endObject();

        return dto;
    }

    private Date nextDate(final JsonReader reader) throws IOException {
        final String dateString = reader.nextString();
        try {
            return FORMATTER.parse(dateString);
        } catch (ParseException e) {
            throw new IOException("Unknown date format: "
                    + dateString, e);
        }
    }
}

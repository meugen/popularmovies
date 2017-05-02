package ua.meugen.android.popularmovies.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.JsonReader;

import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ua.meugen.android.popularmovies.json.JsonReadable;

/**
 * @author meugen
 */

public class NewTokenDto extends BaseResponse implements Parcelable {

    public static final Creator<NewTokenDto> CREATOR
            = new NewTokenDtoCreator();
    public static final JsonReadable<NewTokenDto> READABLE
            = new NewTokenDtoReadable();

    private boolean success;
    private Date expiresAt;
    private String token;

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public void setSuccess(final boolean success) {
        this.success = success;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int flags) {
        _writeToParcel(parcel);
        parcel.writeBooleanArray(new boolean[] { success });
        parcel.writeLong(expiresAt.getTime());
        parcel.writeString(token);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        final NewTokenDto that = (NewTokenDto) o;

        if (success != that.success) return false;
        if (expiresAt != null ? !expiresAt.equals(that.expiresAt) : that.expiresAt != null)
            return false;
        return token != null ? token.equals(that.token) : that.token == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (success ? 1 : 0);
        result = 31 * result + (expiresAt != null ? expiresAt.hashCode() : 0);
        result = 31 * result + (token != null ? token.hashCode() : 0);
        return result;
    }
}

class NewTokenDtoCreator implements Parcelable.Creator<NewTokenDto> {

    @Override
    public NewTokenDto createFromParcel(final Parcel parcel) {
        final NewTokenDto dto = new NewTokenDto();
        dto._readFromParcel(parcel);
        final boolean[] bools = new boolean[1];
        parcel.readBooleanArray(bools);
        dto.setSuccess(bools[0]);
        dto.setExpiresAt(new Date(parcel.readLong()));
        dto.setToken(parcel.readString());
        return dto;
    }

    @Override
    public NewTokenDto[] newArray(final int size) {
        return new NewTokenDto[size];
    }
}

class NewTokenDtoReadable implements JsonReadable<NewTokenDto> {

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss zzz", Locale.ENGLISH);

    @Override
    public NewTokenDto readJson(final JsonReader reader) throws IOException {
        final NewTokenDto dto = new NewTokenDto();

        reader.beginObject();
        while (reader.hasNext()) {
            final String name = reader.nextName();
            if ("success".equals(name)) {
                dto.setSuccess(reader.nextBoolean());
            } else if ("expires_at".equals(name)) {
                dto.setExpiresAt(nextDate(reader));
            } else if ("request_token".equals(name)) {
                dto.setToken(reader.nextString());
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

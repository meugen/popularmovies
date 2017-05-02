package ua.meugen.android.popularmovies.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.JsonReader;

import java.io.IOException;

import ua.meugen.android.popularmovies.json.JsonReadable;

/**
 * @author meugen
 */

public class NewSessionDto extends BaseResponse implements Parcelable {

    public static final Creator<NewSessionDto> CREATOR
            = new NewSessionDtoCreator();
    public static final JsonReadable<NewSessionDto> READABLE
            = new NewSessionDtoReadable();

    private boolean success;
    private String sessionId;

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public void setSuccess(final boolean success) {
        this.success = success;
    }

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
        parcel.writeBooleanArray(new boolean[] { success });
        parcel.writeString(sessionId);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        final NewSessionDto that = (NewSessionDto) o;

        if (success != that.success) return false;
        return sessionId != null ? sessionId.equals(that.sessionId) : that.sessionId == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (success ? 1 : 0);
        result = 31 * result + (sessionId != null ? sessionId.hashCode() : 0);
        return result;
    }
}

class NewSessionDtoCreator implements Parcelable.Creator<NewSessionDto> {

    @Override
    public NewSessionDto createFromParcel(final Parcel parcel) {
        final NewSessionDto dto = new NewSessionDto();
        dto._readFromParcel(parcel);
        final boolean[] bools = new boolean[1];
        parcel.readBooleanArray(bools);
        dto.setSuccess(bools[0]);
        dto.setSessionId(parcel.readString());
        return dto;
    }

    @Override
    public NewSessionDto[] newArray(final int size) {
        return new NewSessionDto[size];
    }
}

class NewSessionDtoReadable implements JsonReadable<NewSessionDto> {

    @Override
    public NewSessionDto readJson(final JsonReader reader) throws IOException {
        final NewSessionDto dto = new NewSessionDto();

        reader.beginObject();
        while (reader.hasNext()) {
            final String name = reader.nextName();
            if ("success".equals(name)) {
                dto.setSuccess(reader.nextBoolean());
            } else if ("session_id".equals(name)) {
                dto.setSessionId(reader.nextString());
            } else {
                dto._readFromJson(reader, name);
            }
        }
        reader.endObject();

        return dto;
    }
}
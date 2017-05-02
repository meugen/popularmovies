package ua.meugen.android.popularmovies.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.JsonReader;

import java.io.IOException;

import ua.meugen.android.popularmovies.json.JsonReadable;

/**
 * @author meugen
 */

public class BaseDto extends BaseResponse implements Parcelable {

    public static final Creator<BaseDto> CREATOR
            = new BaseDtoCreator();
    public static final JsonReadable<BaseDto> READABLE
            = new BaseDtoReadable();

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int flags) {
        _writeToParcel(parcel);
    }
}

class BaseDtoCreator implements Parcelable.Creator<BaseDto> {

    @Override
    public BaseDto createFromParcel(final Parcel parcel) {
        final BaseDto dto = new BaseDto();
        dto._readFromParcel(parcel);
        return dto;
    }

    @Override
    public BaseDto[] newArray(final int size) {
        return new BaseDto[size];
    }
}

class BaseDtoReadable implements JsonReadable<BaseDto> {

    @Override
    public BaseDto readJson(final JsonReader reader) throws IOException {
        final BaseDto dto = new BaseDto();

        reader.beginObject();
        while (reader.hasNext()) {
            final String name = reader.nextName();
            dto._readFromJson(reader, name);
        }
        reader.endObject();

        return dto;
    }
}
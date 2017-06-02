package ua.meugen.android.popularmovies.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author meugen
 */

public class BaseDto extends BaseResponse implements Parcelable {

    public static final Creator<BaseDto> CREATOR
            = new BaseDtoCreator();

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
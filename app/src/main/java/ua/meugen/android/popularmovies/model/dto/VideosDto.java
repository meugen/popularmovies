package ua.meugen.android.popularmovies.model.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;


public class VideosDto extends BaseResponse implements Parcelable {

    public static final Creator<VideosDto> CREATOR
            = new VideosDtoCreator();

    private int id;
    private List<VideoItemDto> results;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public List<VideoItemDto> getResults() {
        return results;
    }

    public void setResults(final List<VideoItemDto> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int flags) {
        parcel.writeInt(this.id);
        parcel.writeTypedList(this.results);
        _writeToParcel(parcel);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        final VideosDto videosDto = (VideosDto) o;

        if (id != videosDto.id) return false;
        return results != null ? results.equals(videosDto.results) : videosDto.results == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id;
        result = 31 * result + (results != null ? results.hashCode() : 0);
        return result;
    }
}

class VideosDtoCreator implements Parcelable.Creator<VideosDto> {

    @Override
    public VideosDto createFromParcel(final Parcel parcel) {
        final VideosDto dto = new VideosDto();
        dto.setId(parcel.readInt());
        final List<VideoItemDto> results = new ArrayList<>();
        parcel.readTypedList(results, VideoItemDto.CREATOR);
        dto.setResults(results);
        dto._readFromParcel(parcel);
        return dto;
    }

    @Override
    public VideosDto[] newArray(final int size) {
        return new VideosDto[size];
    }
}
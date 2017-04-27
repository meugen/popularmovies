package ua.meugen.android.popularmovies.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ua.meugen.android.popularmovies.json.JsonReadable;
import ua.meugen.android.popularmovies.json.JsonUtils;


public class VideosDto extends BaseResponse implements Parcelable {

    public static final Creator<VideosDto> CREATOR
            = new VideosDtoCreator();
    public static final JsonReadable<VideosDto> READABLE
            = new VideosDtoReadable();

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

class VideosDtoReadable implements JsonReadable<VideosDto> {

    @Override
    public VideosDto readJson(final JsonReader reader) throws IOException {
        final VideosDto dto = new VideosDto();

        reader.beginObject();
        while (reader.hasNext()) {
            final String name = reader.nextName();
            if ("id".equals(name)) {
                dto.setId(reader.nextInt());
            } else if ("results".equals(name)) {
                dto.setResults(JsonUtils.nextList(reader, VideoItemDto.READABLE));
            } else {
                dto._readFromJson(reader, name);
            }
        }
        reader.endObject();

        return dto;
    }
}

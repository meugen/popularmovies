package ua.meugen.android.popularmovies.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.JsonReader;

import java.io.IOException;

import ua.meugen.android.popularmovies.json.JsonReadable;

public class VideoItemDto implements Parcelable {

    public static final Creator<VideoItemDto> CREATOR
            = new VideoItemDtoCreator();
    public static final JsonReadable<VideoItemDto> READABLE
            = new VideoItemDtoReadable();

    private String id;
    private String iso6391;
    private String iso31661;
    private String key;
    private String name;
    private String site;
    private int size;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getIso6391() {
        return iso6391;
    }

    public void setIso6391(final String iso6391) {
        this.iso6391 = iso6391;
    }

    public String getIso31661() {
        return iso31661;
    }

    public void setIso31661(final String iso31661) {
        this.iso31661 = iso31661;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(final String site) {
        this.site = site;
    }

    public int getSize() {
        return size;
    }

    public void setSize(final int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int flags) {
        parcel.writeString(this.id);
        parcel.writeString(this.iso6391);
        parcel.writeString(this.iso31661);
        parcel.writeString(this.key);
        parcel.writeString(this.name);
        parcel.writeString(this.site);
        parcel.writeInt(this.size);
        parcel.writeString(this.type);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final VideoItemDto that = (VideoItemDto) o;

        if (size != that.size) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (iso6391 != null ? !iso6391.equals(that.iso6391) : that.iso6391 != null) return false;
        if (iso31661 != null ? !iso31661.equals(that.iso31661) : that.iso31661 != null)
            return false;
        if (key != null ? !key.equals(that.key) : that.key != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (site != null ? !site.equals(that.site) : that.site != null) return false;
        return type != null ? type.equals(that.type) : that.type == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (iso6391 != null ? iso6391.hashCode() : 0);
        result = 31 * result + (iso31661 != null ? iso31661.hashCode() : 0);
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (site != null ? site.hashCode() : 0);
        result = 31 * result + size;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}

class VideoItemDtoCreator implements Parcelable.Creator<VideoItemDto> {

    @Override
    public VideoItemDto createFromParcel(final Parcel parcel) {
        final VideoItemDto dto = new VideoItemDto();
        dto.setId(parcel.readString());
        dto.setIso6391(parcel.readString());
        dto.setIso31661(parcel.readString());
        dto.setKey(parcel.readString());
        dto.setName(parcel.readString());
        dto.setSite(parcel.readString());
        dto.setSize(parcel.readInt());
        dto.setType(parcel.readString());
        return dto;
    }

    @Override
    public VideoItemDto[] newArray(final int size) {
        return new VideoItemDto[size];
    }
}

class VideoItemDtoReadable implements JsonReadable<VideoItemDto> {

    @Override
    public VideoItemDto readJson(final JsonReader reader) throws IOException {
        final VideoItemDto dto = new VideoItemDto();

        reader.beginObject();
        while (reader.hasNext()) {
            final String name = reader.nextName();
            if ("id".equals(name)) {
                dto.setId(reader.nextString());
            } else if ("iso_639_1".equals(name)) {
                dto.setIso6391(reader.nextString());
            } else if ("iso_3166_1".equals(name)) {
                dto.setIso31661(reader.nextString());
            } else if ("key".equals(name)) {
                dto.setKey(reader.nextString());
            } else if ("name".equals(name)) {
                dto.setName(reader.nextString());
            } else if ("site".equals(name)) {
                dto.setSite(reader.nextString());
            } else if ("size".equals(name)) {
                dto.setSize(reader.nextInt());
            } else if ("type".equals(name)) {
                dto.setType(reader.nextString());
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return dto;
    }
}
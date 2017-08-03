package ua.meugen.android.popularmovies.model.responses;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class ReviewItemDto implements Parcelable {

    public static final Creator<ReviewItemDto> CREATOR
            = new ReviewItemDtoCreator();

    @SerializedName("id")
    private String id;
    @SerializedName("author")
    private String author;
    @SerializedName("content")
    private String content;
    @SerializedName("url")
    private String url;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int flags) {
        parcel.writeString(this.id);
        parcel.writeString(this.author);
        parcel.writeString(this.content);
        parcel.writeString(this.url);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final ReviewItemDto that = (ReviewItemDto) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (author != null ? !author.equals(that.author) : that.author != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        return url != null ? url.equals(that.url) : that.url == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }
}

class ReviewItemDtoCreator implements Parcelable.Creator<ReviewItemDto> {

    @Override
    public ReviewItemDto createFromParcel(final Parcel parcel) {
        final ReviewItemDto dto = new ReviewItemDto();
        dto.setId(parcel.readString());
        dto.setAuthor(parcel.readString());
        dto.setContent(parcel.readString());
        dto.setUrl(parcel.readString());
        return dto;
    }

    @Override
    public ReviewItemDto[] newArray(final int size) {
        return new ReviewItemDto[size];
    }
}
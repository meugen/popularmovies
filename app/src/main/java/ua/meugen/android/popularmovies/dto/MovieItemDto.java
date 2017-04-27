package ua.meugen.android.popularmovies.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.JsonReader;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import ua.meugen.android.popularmovies.json.JsonReadable;
import ua.meugen.android.popularmovies.json.JsonUtils;
import ua.meugen.android.popularmovies.utils.ParcelUtils;

/**
 * Created by meugen on 28.03.17.
 */

public class MovieItemDto implements Parcelable {

    public static final Creator<MovieItemDto> CREATOR
            = new MovieItemDtoCreator();
    public static final JsonReadable<MovieItemDto> READABLE
            = new MovieItemDtoReadable();

    private String posterPath;
    private boolean adult;
    private String overview;
    private Date releaseDate;
    private List<Integer> genreIds;
    private int id;
    private String originalTitle;
    private String originalLanguage;
    private String title;
    private String backdropPath;
    private double popularity;
    private int voteCount;
    private boolean video;
    private double voteAverage;

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(final String posterPath) {
        this.posterPath = posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(final boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(final String overview) {
        this.overview = overview;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(final Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(final List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(final String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(final String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(final String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(final double popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(final int voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(final boolean video) {
        this.video = video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(final double voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int flags) {
        parcel.writeString(posterPath);
        parcel.writeString(overview);
        ParcelUtils.writeDate(parcel, releaseDate);
        ParcelUtils.writeIntegerList(parcel, genreIds);
        parcel.writeInt(id);
        parcel.writeString(originalTitle);
        parcel.writeString(originalLanguage);
        parcel.writeString(title);
        parcel.writeString(backdropPath);
        parcel.writeDouble(popularity);
        parcel.writeInt(voteCount);
        parcel.writeDouble(voteAverage);
        parcel.writeBooleanArray(new boolean[] { adult, video });
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final MovieItemDto that = (MovieItemDto) o;

        if (adult != that.adult) return false;
        if (id != that.id) return false;
        if (Double.compare(that.popularity, popularity) != 0) return false;
        if (voteCount != that.voteCount) return false;
        if (video != that.video) return false;
        if (Double.compare(that.voteAverage, voteAverage) != 0) return false;
        if (posterPath != null ? !posterPath.equals(that.posterPath) : that.posterPath != null)
            return false;
        if (overview != null ? !overview.equals(that.overview) : that.overview != null)
            return false;
        if (releaseDate != null ? !releaseDate.equals(that.releaseDate) : that.releaseDate != null)
            return false;
        if (genreIds != null ? !genreIds.equals(that.genreIds) : that.genreIds != null)
            return false;
        if (originalTitle != null ? !originalTitle.equals(that.originalTitle) : that.originalTitle != null)
            return false;
        if (originalLanguage != null ? !originalLanguage.equals(that.originalLanguage) : that.originalLanguage != null)
            return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        return backdropPath != null ? backdropPath.equals(that.backdropPath) : that.backdropPath == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = posterPath != null ? posterPath.hashCode() : 0;
        result = 31 * result + (adult ? 1 : 0);
        result = 31 * result + (overview != null ? overview.hashCode() : 0);
        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        result = 31 * result + (genreIds != null ? genreIds.hashCode() : 0);
        result = 31 * result + id;
        result = 31 * result + (originalTitle != null ? originalTitle.hashCode() : 0);
        result = 31 * result + (originalLanguage != null ? originalLanguage.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (backdropPath != null ? backdropPath.hashCode() : 0);
        temp = Double.doubleToLongBits(popularity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + voteCount;
        result = 31 * result + (video ? 1 : 0);
        temp = Double.doubleToLongBits(voteAverage);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}

class MovieItemDtoReadable implements JsonReadable<MovieItemDto> {

    @Override
    public MovieItemDto readJson(final JsonReader reader) throws IOException {
        final MovieItemDto dto = new MovieItemDto();

        reader.beginObject();
        while (reader.hasNext()) {
            final String name = reader.nextName();
            if ("poster_path".equals(name)) {
                dto.setPosterPath(reader.nextString());
            } else if ("adult".equals(name)) {
                dto.setAdult(reader.nextBoolean());
            } else if ("overview".equals(name)) {
                dto.setOverview(reader.nextString());
            } else if ("release_date".equals(name)) {
                dto.setReleaseDate(JsonUtils.nextDate(reader));
            } else if ("genre_ids".equals(name)) {
                dto.setGenreIds(JsonUtils.nextList(reader, JsonUtils.INTEGER_READABLE));
            } else if ("id".equals(name)) {
                dto.setId(reader.nextInt());
            } else if ("original_title".equals(name)) {
                dto.setOriginalTitle(reader.nextString());
            } else if ("original_language".equals(name)) {
                dto.setOriginalLanguage(reader.nextString());
            } else if ("title".equals(name)) {
                dto.setTitle(reader.nextString());
            } else if ("backdrop_path".equals(name)) {
                dto.setBackdropPath(reader.nextString());
            } else if ("popularity".equals(name)) {
                dto.setPopularity(reader.nextDouble());
            } else if ("vote_count".equals(name)) {
                dto.setVoteCount(reader.nextInt());
            } else if ("video".equals(name)) {
                dto.setVideo(reader.nextBoolean());
            } else if ("vote_average".equals(name)) {
                dto.setVoteAverage(reader.nextDouble());
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return dto;
    }
}

class MovieItemDtoCreator implements Parcelable.Creator<MovieItemDto> {

    @Override
    public MovieItemDto createFromParcel(final Parcel parcel) {
        final MovieItemDto dto = new MovieItemDto();
        dto.setPosterPath(parcel.readString());
        dto.setOverview(parcel.readString());
        dto.setReleaseDate(ParcelUtils.readDate(parcel));
        dto.setGenreIds(ParcelUtils.readerIntegerList(parcel));
        dto.setId(parcel.readInt());
        dto.setOriginalTitle(parcel.readString());
        dto.setOriginalLanguage(parcel.readString());
        dto.setTitle(parcel.readString());
        dto.setBackdropPath(parcel.readString());
        dto.setPopularity(parcel.readDouble());
        dto.setVoteCount(parcel.readInt());
        dto.setVoteAverage(parcel.readDouble());
        final boolean[] bools = new boolean[2];
        parcel.readBooleanArray(bools);
        dto.setAdult(bools[0]);
        dto.setVideo(bools[1]);
        return dto;
    }

    @Override
    public MovieItemDto[] newArray(final int size) {
        return new MovieItemDto[size];
    }
}
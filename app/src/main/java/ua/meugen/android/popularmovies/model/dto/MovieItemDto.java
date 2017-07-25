package ua.meugen.android.popularmovies.model.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import ua.meugen.android.popularmovies.view.utils.ParcelUtils;

/**
 * Created by meugen on 28.03.17.
 */

public class MovieItemDto extends RealmObject implements Parcelable {

    public static final Creator<MovieItemDto> CREATOR
            = new MovieItemDtoCreator();

    private String posterPath;
    private boolean adult;
    private String overview;
    private Date releaseDate;
    @Ignore
    private List<Integer> genreIds;
    @PrimaryKey
    private int id;
    private String originalTitle;
    private String originalLanguage;
    private String title;
    private String backdropPath;
    private double popularity;
    private int voteCount;
    private boolean video;
    private double voteAverage;
    private boolean favorite = false;

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(final boolean favorite) {
        this.favorite = favorite;
    }

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
        parcel.writeBooleanArray(new boolean[] { adult, video, favorite });
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
        if (favorite != that.favorite) return false;
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
        result = 31 * result + (favorite ? 1 : 0);
        return result;
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
        final boolean[] bools = new boolean[3];
        parcel.readBooleanArray(bools);
        dto.setAdult(bools[0]);
        dto.setVideo(bools[1]);
        dto.setFavorite(bools[2]);
        return dto;
    }

    @Override
    public MovieItemDto[] newArray(final int size) {
        return new MovieItemDto[size];
    }
}
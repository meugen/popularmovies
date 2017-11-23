package ua.meugen.android.popularmovies.model.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import ua.meugen.android.popularmovies.model.network.adapters.DateTypeAdapter;

/**
 * Created by meugen on 28.03.17.
 */
@Entity(tableName = "movies")
public class MovieItem {

    @PrimaryKey
    public int id;
    @SerializedName("poster_path")
    public String posterPath;
    public boolean adult;
    public String overview;
    @SerializedName("release_date")
    @JsonAdapter(DateTypeAdapter.class)
    public Date releaseDate;
    @SerializedName("genre_ids")
    @Ignore
    public List<Integer> genreIds;
    @SerializedName("original_title")
    public String originalTitle;
    @SerializedName("original_language")
    public String originalLanguage;
    public String title;
    @SerializedName("backdrop_path")
    public String backdropPath;
    @ColumnInfo(index = true)
    public double popularity;
    @SerializedName("vote_count")
    public int voteCount;
    public boolean video;
    @SerializedName("vote_average")
    @ColumnInfo(index = true)
    public double voteAverage;
    @Expose(serialize = false, deserialize = false)
    public int status = 0;

//    @Override
//    public boolean equals(final Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        final MovieItem that = (MovieItem) o;
//
//        if (adult != that.adult) return false;
//        if (id != that.id) return false;
//        if (Double.compare(that.popularity, popularity) != 0) return false;
//        if (voteCount != that.voteCount) return false;
//        if (video != that.video) return false;
//        if (status != that.status) return false;
//        if (Double.compare(that.voteAverage, voteAverage) != 0) return false;
//        if (posterPath != null ? !posterPath.equals(that.posterPath) : that.posterPath != null)
//            return false;
//        if (overview != null ? !overview.equals(that.overview) : that.overview != null)
//            return false;
//        if (releaseDate != null ? !releaseDate.equals(that.releaseDate) : that.releaseDate != null)
//            return false;
//        if (genreIds != null ? !genreIds.equals(that.genreIds) : that.genreIds != null)
//            return false;
//        if (originalTitle != null ? !originalTitle.equals(that.originalTitle) : that.originalTitle != null)
//            return false;
//        if (originalLanguage != null ? !originalLanguage.equals(that.originalLanguage) : that.originalLanguage != null)
//            return false;
//        if (title != null ? !title.equals(that.title) : that.title != null) return false;
//        return backdropPath != null ? backdropPath.equals(that.backdropPath) : that.backdropPath == null;
//
//    }
//
//    @Override
//    public int hashCode() {
//        int result;
//        long temp;
//        result = posterPath != null ? posterPath.hashCode() : 0;
//        result = 31 * result + (adult ? 1 : 0);
//        result = 31 * result + (overview != null ? overview.hashCode() : 0);
//        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
//        result = 31 * result + (genreIds != null ? genreIds.hashCode() : 0);
//        result = 31 * result + id;
//        result = 31 * result + (originalTitle != null ? originalTitle.hashCode() : 0);
//        result = 31 * result + (originalLanguage != null ? originalLanguage.hashCode() : 0);
//        result = 31 * result + (title != null ? title.hashCode() : 0);
//        result = 31 * result + (backdropPath != null ? backdropPath.hashCode() : 0);
//        temp = Double.doubleToLongBits(popularity);
//        result = 31 * result + (int) (temp ^ (temp >>> 32));
//        result = 31 * result + voteCount;
//        result = 31 * result + (video ? 1 : 0);
//        temp = Double.doubleToLongBits(voteAverage);
//        result = 31 * result + (int) (temp ^ (temp >>> 32));
//        result = 31 * result + status;
//        return result;
//    }
}
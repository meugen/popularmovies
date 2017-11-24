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
}
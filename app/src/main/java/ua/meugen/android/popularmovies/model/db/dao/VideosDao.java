package ua.meugen.android.popularmovies.model.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.Collection;
import java.util.List;

import io.reactivex.Single;
import ua.meugen.android.popularmovies.model.db.entity.MovieItem;
import ua.meugen.android.popularmovies.model.db.entity.VideoItem;

/**
 * Created by meugen on 13.11.2017.
 */

@Dao
public interface VideosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void merge(Collection<VideoItem> videos);

    @Query("SELECT * FROM videos WHERE movie_id=:movieId")
    List<VideoItem> byMovieId(int movieId);

    @Query("SELECT * FROM videos WHERE id=:id")
    VideoItem byId(int id);
}

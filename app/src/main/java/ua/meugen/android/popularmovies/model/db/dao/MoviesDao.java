package ua.meugen.android.popularmovies.model.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;

import java.util.Collection;
import java.util.List;

import ua.meugen.android.popularmovies.model.db.entity.MovieItem;
import ua.meugen.android.popularmovies.model.db.entity.MovieStatus;

/**
 * Created by meugen on 13.11.2017.
 */

@Dao
public interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void merge(Collection<MovieItem> movies);

    @Query("SELECT * FROM movies")
    List<MovieItem> all();

    @Query("SELECT * FROM movies WHERE id=:id")
    MovieItem byId(int id);

    @Query("SELECT * FROM movies WHERE (status&:status)=:status")
    List<MovieItem> byStatus(int status);

    @Query("SELECT * FROM movies WHERE (status&:status)=:status ORDER BY popularity DESC")
    List<MovieItem> mostPopularByStatus(int status);

    @Query("SELECT * FROM movies WHERE (status&:status)=:status ORDER BY voteAverage DESC")
    List<MovieItem> topRatedByStatus(int status);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT status FROM movies WHERE id=:id")
    MovieStatus statusById(int id);
}

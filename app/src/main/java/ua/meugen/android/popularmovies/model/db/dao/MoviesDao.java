package ua.meugen.android.popularmovies.model.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;

import java.util.Collection;
import java.util.List;

import ua.meugen.android.popularmovies.model.db.entity.EntityCount;
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

    @Query("SELECT * FROM movies WHERE (status&:status)=:status LIMIT :limit OFFSET :offset")
    List<MovieItem> byStatus(int status, int limit, int offset);

    @Query("SELECT * FROM movies WHERE (status&:status)=:status ORDER BY popularity DESC LIMIT :limit OFFSET :offset")
    List<MovieItem> mostPopularByStatus(int status, int limit, int offset);

    @Query("SELECT * FROM movies WHERE (status&:status)=:status ORDER BY voteAverage DESC LIMIT :limit OFFSET :offset")
    List<MovieItem> topRatedByStatus(int status, int limit, int offset);

    @Query("SELECT count(id) c FROM movies WHERE (status&:status)=:status")
    EntityCount countByStatus(int status);

    @Query("UPDATE movies SET status=status&~:status")
    void resetStatus(int status);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT status FROM movies WHERE id=:id")
    MovieStatus statusById(int id);
}

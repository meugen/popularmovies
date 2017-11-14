package ua.meugen.android.popularmovies.model.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.Collection;
import java.util.List;

import io.reactivex.Single;
import ua.meugen.android.popularmovies.model.db.entity.ReviewItem;

/**
 * Created by meugen on 13.11.2017.
 */

@Dao
public interface ReviewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void merge(Collection<ReviewItem> reviews);

    @Query("SELECT * FROM reviews WHERE movie_id=:movieId")
    List<ReviewItem> byMovieId(int movieId);

    @Query("SELECT * FROM reviews WHERE id=:id")
    ReviewItem byId(String id);
}

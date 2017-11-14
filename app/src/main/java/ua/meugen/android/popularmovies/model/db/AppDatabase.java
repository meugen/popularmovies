package ua.meugen.android.popularmovies.model.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import ua.meugen.android.popularmovies.model.db.dao.MoviesDao;
import ua.meugen.android.popularmovies.model.db.dao.ReviewsDao;
import ua.meugen.android.popularmovies.model.db.dao.VideosDao;
import ua.meugen.android.popularmovies.model.db.entity.MovieItem;
import ua.meugen.android.popularmovies.model.db.entity.ReviewItem;
import ua.meugen.android.popularmovies.model.db.entity.VideoItem;

/**
 * Created by meugen on 13.11.2017.
 */
@Database(entities = {MovieItem.class, ReviewItem.class, VideoItem.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MoviesDao moviesDao();

    public abstract ReviewsDao reviewsDao();

    public abstract VideosDao videosDao();
}

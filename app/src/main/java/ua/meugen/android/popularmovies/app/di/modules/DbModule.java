package ua.meugen.android.popularmovies.app.di.modules;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ua.meugen.android.popularmovies.model.db.AppDatabase;
import ua.meugen.android.popularmovies.model.db.dao.MoviesDao;
import ua.meugen.android.popularmovies.model.db.dao.ReviewsDao;
import ua.meugen.android.popularmovies.model.db.dao.VideosDao;

/**
 * @author meugen
 */
@Module
public abstract class DbModule {

    @Provides @Singleton
    static AppDatabase provideAppDatabase(
            @Named(AppModule.APP_CONTEXT) final Context context) {
        return Room
                .databaseBuilder(context, AppDatabase.class, "popularmovies")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides @Singleton
    static MoviesDao provideMoviesDao(final AppDatabase db) {
        return db.moviesDao();
    }

    @Provides @Singleton
    static ReviewsDao provideReviewsDao(final AppDatabase db) {
        return db.reviewsDao();
    }

    @Provides @Singleton
    static VideosDao provideVideosDao(final AppDatabase db) {
        return db.videosDao();
    }
}

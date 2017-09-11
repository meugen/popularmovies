package ua.meugen.android.popularmovies.app.di.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

import ua.meugen.android.popularmovies.app.di.db.movie.MovieContract;

/**
 * @author meugen
 */
@Singleton
public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String NAME = "popularmovies.db";
    private static final int VERSION = 1;

    @Inject
    public DbOpenHelper(final Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + MovieContract.TABLE + " ( " +
                MovieContract.Fields.ID + " INTEGER NOT NULL PRIMARY KEY, " +
                MovieContract.Fields.POSTER_PATH + " VARCHAR(200) NOT NULL, " +
                MovieContract.Fields.ADULT + " TINYINT NOT NULL, " +
                MovieContract.Fields.OVERVIEW + " TEXT NOT NULL, " +
                MovieContract.Fields.RELEASE_DATE + " BIGINT NOT NULL, " +
                MovieContract.Fields.ORIGINAL_TITLE + " VARCHAR(100) NOT NULL, " +
                MovieContract.Fields.ORIGINAL_LANGUAGE + " VARCHAR(20) NOT NULL, " +
                MovieContract.Fields.TITLE + " VARCHAR(100) NOT NULL, " +
                MovieContract.Fields.BACKDROP_PATH + " VARCHAR(200) NOT NULL, " +
                MovieContract.Fields.POPULARITY + " DOUBLE NOT NULL, " +
                MovieContract.Fields.VOTE_COUNT + " INTEGER NOT NULL, " +
                MovieContract.Fields.VIDEO + " TINYINT NOT NULL, " +
                MovieContract.Fields.VOTE_AVERAGE + " DOUBLE NOT NULL, " +
                MovieContract.Fields.STATUS + " TINYINT NOT NULL);");
    }

    @Override
    public void onUpgrade(
            final SQLiteDatabase db,
            final int oldVersion,
            final int newVersion) {
        db.execSQL("DROP TABLE " + MovieContract.TABLE + ";");
        onCreate(db);
    }
}
package ua.meugen.android.popularmovies.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by meugen on 06.05.17.
 */

public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String NAME = "popularmovies.db";
    private static final int VERSION = 1;

    public DbOpenHelper(final Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        db.execSQL("CREATE TABLE movies (" +
                " id INTEGER NOT NULL UNIQUE," +
                " poster_path VARCHAR(200) NOT NULL," +
                " adult TINYINT NOT NULL," +
                " overview TEXT NOT NULL," +
                " release_date BIGINT NOT NULL," +
                " original_title VARCHAR(200) NOT NULL," +
                " original_language VARCHAR(200) NOT NULL," +
                " title VARCHAR(200) NOT NULL," +
                " backdrop_path VARCHAR(200) NOT NULL," +
                " popularity DOUBLE NOT NULL," +
                " vote_count INTEGER NOT NULL," +
                " video TINYINT NOT NULL," +
                " vote_average DOUBLE NOT NULL)");
        db.execSQL("CREATE TABLE movie_types (" +
                " movie_id INTEGER NOT NULL," +
                " type VARCHAR(20) NOT NULL," +
                " PRIMARY KEY (movie_id, type))");
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        db.execSQL("DROP TABLE movies");
        db.execSQL("DROP TABLE movie_types");
        onCreate(db);
    }
}

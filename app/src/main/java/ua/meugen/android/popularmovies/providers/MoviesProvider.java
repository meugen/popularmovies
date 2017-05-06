package ua.meugen.android.popularmovies.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import ua.meugen.android.popularmovies.app.DbOpenHelper;

public class MoviesProvider extends ContentProvider implements MoviesContract {

    private static final int ID_POPULAR = 1;
    private static final int ID_TOP_RATED = 2;
    private static final int ID_FAVORITES = 3;
    private static final int ID_MOVIES_BY_ID = 4;

    private static UriMatcher uriMatcher;
    private DbOpenHelper openHelper;

    public MoviesProvider() {
    }

    @Override
    public int delete(final Uri uri, final String selection, final String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(final Uri uri) {
        return "application/movie";
    }

    @Override
    public Uri insert(final Uri uri, final ContentValues values) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        if (uriMatcher == null) {
            uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
            uriMatcher.addURI(AUTHORITY, POPULAR, ID_POPULAR);
            uriMatcher.addURI(AUTHORITY, TOP_RATED, ID_TOP_RATED);
            uriMatcher.addURI(AUTHORITY, FAVORITES, ID_FAVORITES);
            uriMatcher.addURI(AUTHORITY, MOVIES + "/#", ID_MOVIES_BY_ID);
        }
        openHelper = new DbOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(final Uri uri, final String[] projection, final String selection,
                        final String[] selectionArgs, final String sortOrder) {
        Cursor cursor;

        final int match = uriMatcher.match(uri);
        if (match == ID_POPULAR) {
            cursor = queryMoviesByType(projection, POPULAR);
        } else if (match == ID_TOP_RATED) {
            cursor = queryMoviesByType(projection, TOP_RATED);
        } else if (match == ID_FAVORITES) {
            cursor = queryMoviesByType(projection, FAVORITES);
        } else if (match == ID_MOVIES_BY_ID) {
            cursor = queryMoviesById(projection, uri.getLastPathSegment());
        } else {
            throw new IllegalArgumentException("Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    private Cursor queryMoviesByType(final String[] projection, final String type) {
        final SQLiteDatabase db = openHelper.getReadableDatabase();
        return db.query(TABLE_MOVIES, projection, SELECTION_BY_TYPE,
                new String[] { type }, null, null, null);
    }

    private Cursor queryMoviesById(final String[] projection, final String id) {
        final SQLiteDatabase db = openHelper.getReadableDatabase();
        return db.query(TABLE_MOVIES, projection, SELECTION_BY_ID,
                new String[] { id }, null, null, null);
    }

    @Override
    public int update(final Uri uri, final ContentValues values, final String selection,
                      final String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int bulkInsert(@NonNull final Uri uri, @NonNull final ContentValues[] values) {
        int result;

        final int match = uriMatcher.match(uri);
        if (match == ID_POPULAR) {
            result = bulkInsertWithType(values, POPULAR);
        } else if (match == ID_TOP_RATED) {
            result = bulkInsertWithType(values, TOP_RATED);
        } else if (match == ID_FAVORITES) {
            result = bulkInsertWithType(values, FAVORITES);
        } else {
            throw new IllegalArgumentException("Unknow uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }

    private int bulkInsertWithType(final ContentValues[] items, final String type) {
        final SQLiteDatabase db = openHelper.getWritableDatabase();

        db.beginTransaction();
        try {
            db.delete(TABLE_MOVIE_TYPES, FIELD_TYPE + "=?", new String[] { type });
            for (ContentValues item : items) {
                db.insertWithOnConflict(TABLE_MOVIES, null, item,
                        SQLiteDatabase.CONFLICT_IGNORE);
                final int id = item.getAsInteger(FIELD_ID);

                final ContentValues movieType = new ContentValues();
                movieType.put(FIELD_MOVIE_ID, id);
                movieType.put(FIELD_TYPE, type);
                db.insertOrThrow(TABLE_MOVIE_TYPES, null, movieType);
            }
            db.setTransactionSuccessful();
            return items.length;
        } finally {
            db.endTransaction();
        }
    }
}

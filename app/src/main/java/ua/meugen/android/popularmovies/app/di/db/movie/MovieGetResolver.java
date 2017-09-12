package ua.meugen.android.popularmovies.app.di.db.movie;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.operations.get.DefaultGetResolver;

import java.util.Date;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.model.responses.MovieItemDto;

/**
 * @author meugen
 */

public class MovieGetResolver extends DefaultGetResolver<MovieItemDto>
        implements MovieContract {

    @Inject
    public MovieGetResolver() {}

    @NonNull
    @Override
    public MovieItemDto mapFromCursor(@NonNull final Cursor cursor) {
        final MovieItemDto result = new MovieItemDto();
        result.setId(cursor.getInt(cursor.getColumnIndex(FIELD_ID)));
        result.setPosterPath(cursor.getString(cursor.getColumnIndex(FIELD_POSTER_PATH)));
        result.setAdult(cursor.getInt(cursor.getColumnIndex(FIELD_ADULT)) == INT_TRUE);
        result.setOverview(cursor.getString(cursor.getColumnIndex(FIELD_OVERVIEW)));
        result.setReleaseDate(new Date(cursor.getLong(cursor.getColumnIndex(FIELD_RELEASE_DATE))));
        result.setOriginalTitle(cursor.getString(cursor.getColumnIndex(FIELD_ORIGINAL_TITLE)));
        result.setOriginalLanguage(cursor.getString(cursor.getColumnIndex(FIELD_ORIGINAL_LANGUAGE)));
        result.setTitle(cursor.getString(cursor.getColumnIndex(FIELD_TITLE)));
        result.setBackdropPath(cursor.getString(cursor.getColumnIndex(FIELD_BACKDROP_PATH)));
        result.setPopularity(cursor.getDouble(cursor.getColumnIndex(FIELD_POPULARITY)));
        result.setVoteCount(cursor.getInt(cursor.getColumnIndex(FIELD_VOTE_COUNT)));
        result.setVideo(cursor.getInt(cursor.getColumnIndex(FIELD_VIDEO)) == INT_TRUE);
        result.setVoteAverage(cursor.getDouble(cursor.getColumnIndex(FIELD_VOTE_AVERAGE)));
        result.setStatus(cursor.getInt(cursor.getColumnIndex(FIELD_STATUS)));
        return result;
    }
}

package ua.meugen.android.popularmovies.app.di.db.movie;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.operations.put.DefaultPutResolver;
import com.pushtorefresh.storio.sqlite.queries.InsertQuery;
import com.pushtorefresh.storio.sqlite.queries.UpdateQuery;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.model.responses.MovieItemDto;

/**
 * @author meugen
 */

public class MoviePutResolver extends DefaultPutResolver<MovieItemDto> implements MovieContract {

    @Inject
    public MoviePutResolver() {}

    @NonNull
    @Override
    protected InsertQuery mapToInsertQuery(@NonNull final MovieItemDto object) {
        return InsertQuery.builder()
                .table(TABLE)
                .nullColumnHack(null)
                .build();
    }

    @NonNull
    @Override
    protected UpdateQuery mapToUpdateQuery(@NonNull final MovieItemDto object) {
        return UpdateQuery.builder()
                .table(TABLE)
                .where(FIELD_ID + "=?")
                .whereArgs(object.getId())
                .build();
    }

    @NonNull
    @Override
    protected ContentValues mapToContentValues(@NonNull final MovieItemDto object) {
        final ContentValues result = new ContentValues();
        result.put(FIELD_ID, object.getId());
        result.put(FIELD_POSTER_PATH, object.getPosterPath());
        result.put(FIELD_ADULT, object.isAdult());
        result.put(FIELD_OVERVIEW, object.getOverview());
        result.put(FIELD_RELEASE_DATE, object.getReleaseDate().getTime());
        result.put(FIELD_ORIGINAL_TITLE, object.getOriginalTitle());
        result.put(FIELD_ORIGINAL_LANGUAGE, object.getOriginalLanguage());
        result.put(FIELD_TITLE, object.getTitle());
        result.put(FIELD_BACKDROP_PATH, object.getBackdropPath());
        result.put(FIELD_POPULARITY, object.getPopularity());
        result.put(FIELD_VOTE_COUNT, object.getVoteCount());
        result.put(FIELD_VIDEO, object.isVideo());
        result.put(FIELD_VOTE_AVERAGE, object.getVoteAverage());
        result.put(FIELD_STATUS, object.getStatus());
        return result;
    }
}

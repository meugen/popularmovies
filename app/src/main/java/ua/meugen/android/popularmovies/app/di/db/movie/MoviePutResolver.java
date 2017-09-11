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
                .where(Fields.ID + "=?")
                .whereArgs(object.getId())
                .build();
    }

    @NonNull
    @Override
    protected ContentValues mapToContentValues(@NonNull final MovieItemDto object) {
        final ContentValues result = new ContentValues();
        result.put(Fields.ID, object.getId());
        result.put(Fields.POSTER_PATH, object.getPosterPath());
        result.put(Fields.ADULT, object.isAdult());
        result.put(Fields.OVERVIEW, object.getOverview());
        result.put(Fields.RELEASE_DATE, object.getReleaseDate().getTime());
        result.put(Fields.ORIGINAL_TITLE, object.getOriginalTitle());
        result.put(Fields.ORIGINAL_LANGUAGE, object.getOriginalLanguage());
        result.put(Fields.TITLE, object.getTitle());
        result.put(Fields.BACKDROP_PATH, object.getBackdropPath());
        result.put(Fields.POPULARITY, object.getPopularity());
        result.put(Fields.VOTE_COUNT, object.getVoteCount());
        result.put(Fields.VIDEO, object.isVideo());
        result.put(Fields.VOTE_AVERAGE, object.getVoteAverage());
        result.put(Fields.STATUS, object.getStatus());
        return result;
    }
}

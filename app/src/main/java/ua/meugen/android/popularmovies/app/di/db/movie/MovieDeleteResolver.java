package ua.meugen.android.popularmovies.app.di.db.movie;

import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.operations.delete.DefaultDeleteResolver;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.model.responses.MovieItemDto;

/**
 * @author meugen
 */

public class MovieDeleteResolver extends DefaultDeleteResolver<MovieItemDto> implements MovieContract {

    @Inject
    public MovieDeleteResolver() {}

    @NonNull
    @Override
    protected DeleteQuery mapToDeleteQuery(@NonNull final MovieItemDto object) {
        return DeleteQuery.builder()
                .table(TABLE)
                .where(FIELD_ID + "=?")
                .whereArgs(object.getId())
                .build();
    }
}

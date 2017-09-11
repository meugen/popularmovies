package ua.meugen.android.popularmovies.app.di.db.movie;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.operations.get.DefaultGetResolver;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.model.responses.MovieItemDto;

/**
 * @author meugen
 */

public class MovieGetResolver extends DefaultGetResolver<MovieItemDto> {

    @Inject
    public MovieGetResolver() {}

    @NonNull
    @Override
    public MovieItemDto mapFromCursor(@NonNull final Cursor cursor) {
        return null;
    }
}

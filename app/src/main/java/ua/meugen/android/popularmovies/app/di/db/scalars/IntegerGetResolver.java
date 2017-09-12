package ua.meugen.android.popularmovies.app.di.db.scalars;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.operations.get.DefaultGetResolver;

import javax.inject.Inject;

/**
 * @author meugen
 */

public class IntegerGetResolver extends DefaultGetResolver<Integer> {

    @Inject
    public IntegerGetResolver() {}

    @NonNull
    @Override
    public Integer mapFromCursor(@NonNull final Cursor cursor) {
        return cursor.getInt(0);
    }
}

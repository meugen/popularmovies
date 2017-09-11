package ua.meugen.android.popularmovies.app.executors;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.get.GetResolver;
import com.pushtorefresh.storio.sqlite.queries.Query;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.model.responses.MovieItemDto;

/**
 * @author meugen
 */

public class MergeMoviesExecutor extends AbstractExecutor<MoviesData> {

    private final GetResolver<Integer> statusGetResolver;

    @Inject
    public MergeMoviesExecutor(final GetResolver<Integer> statusGetResolver) {
        this.statusGetResolver = statusGetResolver;
    }

    @Override
    protected void execute(final StorIOSQLite storIOSQLite, final MoviesData data) {
        if (!data.isNeedToSave()) {
            throw new IllegalArgumentException("This data no need to save.");
        }
        final Query.CompleteBuilder builder = Query.builder()
                .table("movies")
                .columns("status")
                .where("id=?")
                .limit(1);
        for (MovieItemDto movie : data.getMovies()) {
            Integer status = storIOSQLite
                    .get().object(Integer.class)
                    .withQuery(builder.whereArgs(movie.getId()).build())
                    .withGetResolver(statusGetResolver)
                    .prepare().executeAsBlocking();
            if (status != null) {
                movie.setStatus(data.getStatus() | status);
            }
            storIOSQLite.put().object(movie).prepare().executeAsBlocking();
        }
    }
}

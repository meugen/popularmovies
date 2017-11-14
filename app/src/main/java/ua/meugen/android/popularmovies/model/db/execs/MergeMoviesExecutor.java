package ua.meugen.android.popularmovies.model.db.execs;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.model.db.dao.MoviesDao;
import ua.meugen.android.popularmovies.model.db.entity.MovieItem;
import ua.meugen.android.popularmovies.model.db.entity.MovieStatus;

/**
 * @author meugen
 */

public class MergeMoviesExecutor extends AbstractExecutor<MoviesData> {

    @Inject MoviesDao moviesDao;

    @Inject
    MergeMoviesExecutor() {}

    @Override
    protected void execute(final MoviesData data) {
        if (!data.isNeedToSave()) {
            throw new IllegalArgumentException("This data no need to save.");
        }
        for (MovieItem movie : data.getMovies()) {
            MovieStatus movieStatus = moviesDao.statusById(movie.id);
            if (movieStatus != null) {
                movie.status = data.getStatus() | movieStatus.status;
            }
        }
        moviesDao.merge(data.getMovies());
    }
}

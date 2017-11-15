package ua.meugen.android.popularmovies.model.db.execs;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.model.db.dao.MoviesDao;
import ua.meugen.android.popularmovies.model.db.entity.MovieItem;
import ua.meugen.android.popularmovies.model.db.entity.MovieStatus;
import ua.meugen.android.popularmovies.model.db.execs.data.MoviesData;

/**
 * @author meugen
 */

public class MergeMoviesExecutor extends AbstractExecutor<MoviesData> {

    @Inject MoviesDao moviesDao;

    @Inject
    MergeMoviesExecutor() {}

    @Override
    protected void execute(final MoviesData data) {
        for (MovieItem movie : data.movies) {
            MovieStatus movieStatus = moviesDao.statusById(movie.id);
            if (movieStatus != null) {
                movie.status = data.status | movieStatus.status;
            }
        }
        moviesDao.merge(data.movies);
    }
}

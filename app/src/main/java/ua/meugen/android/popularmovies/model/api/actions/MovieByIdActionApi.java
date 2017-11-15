package ua.meugen.android.popularmovies.model.api.actions;

import javax.inject.Inject;

import io.reactivex.Observable;
import ua.meugen.android.popularmovies.model.api.AppActionApi;
import ua.meugen.android.popularmovies.model.db.dao.MoviesDao;
import ua.meugen.android.popularmovies.model.db.entity.MovieItem;

/**
 * Created by meugen on 15.11.2017.
 */

public class MovieByIdActionApi implements AppActionApi<Integer, MovieItem> {

    @Inject MoviesDao moviesDao;

    @Inject
    MovieByIdActionApi() {}

    @Override
    public Observable<MovieItem> action(final Integer id) {
        return Observable.fromCallable(() -> moviesDao.byId(id));
    }
}

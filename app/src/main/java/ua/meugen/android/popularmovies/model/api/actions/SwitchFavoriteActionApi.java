package ua.meugen.android.popularmovies.model.api.actions;

import java.util.Collections;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import ua.meugen.android.popularmovies.model.SortType;
import ua.meugen.android.popularmovies.model.api.AppActionApi;
import ua.meugen.android.popularmovies.model.cache.Cache;
import ua.meugen.android.popularmovies.model.cache.KeyGenerator;
import ua.meugen.android.popularmovies.model.db.dao.MoviesDao;
import ua.meugen.android.popularmovies.model.db.entity.MovieItem;

/**
 * Created by meugen on 27.11.2017.
 */

public class SwitchFavoriteActionApi implements AppActionApi<MovieItem, Void> {

    @Inject Cache cache;
    @Inject KeyGenerator<Integer> keyGenerator;
    @Inject MoviesDao moviesDao;

    @Inject
    SwitchFavoriteActionApi() {}

    @Override
    public Observable<Void> action(final MovieItem movie) {
        return Observable.create(e -> switchFavorite(movie, e));
    }

    private void switchFavorite(
            final MovieItem movie,
            final ObservableEmitter<Void> emitter) {
        if ((movie.status & SortType.FAVORITES) == SortType.FAVORITES) {
            movie.status = movie.status & ~SortType.FAVORITES;
        } else {
            movie.status = movie.status | SortType.FAVORITES;
        }
        moviesDao.merge(Collections.singleton(movie));
        cache.clear(keyGenerator.generateKey(SortType.FAVORITES));
        if (!emitter.isDisposed()) {
            emitter.onComplete();
        }
    }
}

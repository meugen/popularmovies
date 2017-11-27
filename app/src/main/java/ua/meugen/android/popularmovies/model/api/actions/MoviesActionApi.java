package ua.meugen.android.popularmovies.model.api.actions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import ua.meugen.android.popularmovies.model.SortType;
import ua.meugen.android.popularmovies.model.api.ServerApi;
import ua.meugen.android.popularmovies.model.cache.KeyGenerator;
import ua.meugen.android.popularmovies.model.db.dao.MoviesDao;
import ua.meugen.android.popularmovies.model.db.entity.MovieItem;
import ua.meugen.android.popularmovies.model.db.execs.Executor;
import ua.meugen.android.popularmovies.model.db.execs.data.MoviesData;
import ua.meugen.android.popularmovies.model.network.resp.PagedMoviesResponse;

/**
 * Created by meugen on 15.11.2017.
 */

public class MoviesActionApi extends OfflineFirstActionApi<Integer, List<MovieItem>> {

    @Inject ServerApi serverApi;
    @Inject MoviesDao moviesDao;
    @Inject Executor<MoviesData> mergeMoviesExecutor;
    @Inject KeyGenerator<Integer> keyGenerator;

    @Inject
    MoviesActionApi() {}

    @NonNull
    @Override
    Single<List<MovieItem>> offlineData(final Integer status) {
        return Single.just(status).flatMap(this::moviesByStatus);
    }

    private Single<List<MovieItem>> moviesByStatus(Integer status) {
        Callable<List<MovieItem>> callable;

        int _status = status;
        if (_status == SortType.POPULAR) {
            callable = () -> moviesDao.mostPopularByStatus(_status);
        } else if (_status == SortType.TOP_RATED) {
            callable = () -> moviesDao.topRatedByStatus(_status);
        } else {
            callable = () -> moviesDao.byStatus(_status);
        }
        return Single.fromCallable(callable);
    }

    @Nullable
    @Override
    Single<List<MovieItem>> networkData(final Integer status) {
        final int _status = status;

        Single<PagedMoviesResponse> single = null;
        if (_status == SortType.POPULAR) {
            single = serverApi.getPopularMovies();
        } else if (_status == SortType.TOP_RATED) {
            single = serverApi.getTopRatedMovies();
        }
        return single == null ? null : single.map(PagedMoviesResponse::getResults);
    }

    @Override
    void storeOffline(final Integer status, final List<MovieItem> movies) {
        final Disposable disposable = mergeMoviesExecutor
                .executeTransaction(new MoviesData(movies, status))
                .subscribe();
        getCompositeDisposable().add(disposable);
    }

    @NonNull
    @Override
    String cacheKey(final Integer status) {
        return keyGenerator.generateKey(status);
    }
}

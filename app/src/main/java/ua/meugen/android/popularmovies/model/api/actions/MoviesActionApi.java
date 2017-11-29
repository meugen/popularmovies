package ua.meugen.android.popularmovies.model.api.actions;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import ua.meugen.android.popularmovies.BuildConfig;
import ua.meugen.android.popularmovies.model.SortType;
import ua.meugen.android.popularmovies.model.api.ServerApi;
import ua.meugen.android.popularmovies.model.api.req.MoviesReq;
import ua.meugen.android.popularmovies.model.cache.Cache;
import ua.meugen.android.popularmovies.model.cache.KeyGenerator;
import ua.meugen.android.popularmovies.model.config.Config;
import ua.meugen.android.popularmovies.model.db.dao.MoviesDao;
import ua.meugen.android.popularmovies.model.db.entity.MovieItem;
import ua.meugen.android.popularmovies.model.db.execs.Executor;
import ua.meugen.android.popularmovies.model.db.execs.data.MoviesData;
import ua.meugen.android.popularmovies.model.network.resp.PagedMoviesResponse;

/**
 * Created by meugen on 15.11.2017.
 */

public class MoviesActionApi extends OfflineFirstActionApi<MoviesReq, List<MovieItem>> {

    @Inject Cache cache;
    @Inject Config config;
    @Inject ServerApi serverApi;
    @Inject MoviesDao moviesDao;
    @Inject Executor<MoviesData> mergeMoviesExecutor;
    @Inject KeyGenerator<Integer> keyGenerator;

    @Inject
    MoviesActionApi() {}

    @NonNull
    @Override
    Single<List<MovieItem>> offlineData(final MoviesReq req) {
        return Single.just(req)
                .flatMap(this::moviesByStatus)
                .map(this::checkOfflineEmpty);
    }

    private List<MovieItem> checkOfflineEmpty(final List<MovieItem> movies) {
        if (movies.isEmpty()) {
            throw new IllegalArgumentException("Offline data is empty.");
        }
        return movies;
    }

    private Single<List<MovieItem>> moviesByStatus(final MoviesReq req) {
        Callable<List<MovieItem>> callable;

        int offset = (req.page - 1) * BuildConfig.PAGE_SIZE;
        if (req.status == SortType.POPULAR) {
            callable = () -> moviesDao.mostPopularByStatus(
                    req.status, BuildConfig.PAGE_SIZE, offset);
        } else if (req.status == SortType.TOP_RATED) {
            callable = () -> moviesDao.topRatedByStatus(
                    req.status, BuildConfig.PAGE_SIZE, offset);
        } else {
            callable = () -> moviesDao.byStatus(req.status,
                    BuildConfig.PAGE_SIZE, offset);
        }
        return Single.fromCallable(callable);
    }

    @NonNull
    @Override
    Single<List<MovieItem>> networkData(final MoviesReq req) {
        Single<List<MovieItem>> single;
        if (req.status == SortType.POPULAR) {
            single = serverApi.getPopularMovies(config.getLanguage(), req.page)
                    .map(this::checkResponse);
        } else if (req.status == SortType.TOP_RATED) {
            single = serverApi.getTopRatedMovies(config.getLanguage(), req.page)
                    .map(this::checkResponse);
        } else if (req.status == SortType.FAVORITES) {
            single = Single.<List<MovieItem>>just(Collections.emptyList());
        } else {
            throw new IllegalArgumentException("Unknown status: " + req.status);
        }
        return single;
    }

    private List<MovieItem> checkResponse(final PagedMoviesResponse response) {
        if (!response.isSuccess()) {
            throw new IllegalArgumentException("Not success: code = "
                    + response.getStatusCode() + ", message = " + response.getStatusMessage());
        }
        return response.getResults();
    }

    @Override
    void storeOffline(final MoviesReq req, final List<MovieItem> movies) {
        final Disposable disposable = mergeMoviesExecutor
                .executeTransaction(new MoviesData(movies, req.status))
                .subscribe();
        getCompositeDisposable().add(disposable);
    }

    @Override
    public void clearCache(final MoviesReq req) {
        cache.clear(keyGenerator.generateKey(req.status));
        moviesDao.resetStatus(req.status);
    }

    @NonNull
    @Override
    List<MovieItem> retrieveCache(final MoviesReq req) {
        if (req.page != 1) {
            throw new IllegalArgumentException("Cache retrieving only for page 1");
        }
        List<MovieItem> movies = cache.get(keyGenerator.generateKey(req.status));
        if (movies == null) {
            throw new IllegalArgumentException("No cache was found for movies with status " + req.status);
        }
        return movies;
    }

    @Override
    List<MovieItem> storeCache(final MoviesReq req, final List<MovieItem> movies) {
        String key = keyGenerator.generateKey(req.status);
        List<MovieItem> cachedMovies = cache.get(key);
        if (req.page == 1 || cachedMovies == null) {
            cache.set(key, movies);
            cachedMovies = movies;
        } else {
            cachedMovies.addAll(movies);
        }
        return new ArrayList<>(cachedMovies);
    }
}

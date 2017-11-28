package ua.meugen.android.popularmovies.model.api.actions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import ua.meugen.android.popularmovies.BuildConfig;
import ua.meugen.android.popularmovies.model.SortType;
import ua.meugen.android.popularmovies.model.api.ServerApi;
import ua.meugen.android.popularmovies.model.api.req.MoviesReq;
import ua.meugen.android.popularmovies.model.cache.KeyGenerator;
import ua.meugen.android.popularmovies.model.config.Config;
import ua.meugen.android.popularmovies.model.db.dao.MoviesDao;
import ua.meugen.android.popularmovies.model.db.entity.EntityCount;
import ua.meugen.android.popularmovies.model.db.entity.MovieItem;
import ua.meugen.android.popularmovies.model.db.execs.Executor;
import ua.meugen.android.popularmovies.model.db.execs.data.MoviesData;
import ua.meugen.android.popularmovies.model.network.resp.PagedMoviesResponse;

/**
 * Created by meugen on 15.11.2017.
 */

public class MoviesActionApi extends OfflineFirstActionApi<MoviesReq, PagedMoviesResponse> {

    @Inject Config config;
    @Inject ServerApi serverApi;
    @Inject MoviesDao moviesDao;
    @Inject Executor<MoviesData> mergeMoviesExecutor;
    @Inject KeyGenerator<Integer> keyGenerator;

    @Inject
    MoviesActionApi() {}

    @NonNull
    @Override
    Single<PagedMoviesResponse> offlineData(final MoviesReq req) {
        return Single.just(req).flatMap(this::moviesByStatus);
    }

    private Single<PagedMoviesResponse> moviesByStatus(final MoviesReq req) {
        Callable<List<MovieItem>> callable;

        int offset = (req.page - 1) * BuildConfig.PAGE_SIZE;
        if (req.status == SortType.POPULAR) {
            callable = () -> moviesDao.mostPopularByStatus(
                    req.status, BuildConfig.PAGE_SIZE, offset);
        } else if (req.status == SortType.TOP_RATED) {
            callable = () -> moviesDao.topRatedByStatus(
                    req.status, BuildConfig.PAGE_SIZE, offset);
        } else {
            callable = () -> moviesDao.byStatus(req.status);
        }
        return Single.fromCallable(callable)
                .map(results -> resultsToResponse(req, results));
    }

    private PagedMoviesResponse resultsToResponse(
            final MoviesReq req, final List<MovieItem> results) {
        EntityCount entityCount = moviesDao.countByStatus(req.status);
        int totalPages = entityCount.count / BuildConfig.PAGE_SIZE;
        if (entityCount.count % BuildConfig.PAGE_SIZE != 0) {
            totalPages++;
        }

        PagedMoviesResponse response = new PagedMoviesResponse();
        response.setResults(results);
        response.setTotalPages(totalPages);
        response.setTotalResults(entityCount.count);
        response.setPage(1);
        return response;
    }

    @Nullable
    @Override
    Single<PagedMoviesResponse> networkData(final MoviesReq req) {
        Single<PagedMoviesResponse> single = null;
        if (req.status == SortType.POPULAR) {
            single = serverApi.getPopularMovies(config.getLanguage(), req.page);
        } else if (req.status == SortType.TOP_RATED) {
            single = serverApi.getTopRatedMovies(config.getLanguage(), req.page);
        }
        return single;
    }

    @Override
    void storeOffline(final MoviesReq req, final PagedMoviesResponse response) {
        final Disposable disposable = mergeMoviesExecutor
                .executeTransaction(new MoviesData(response.getResults(), req.status))
                .subscribe();
        getCompositeDisposable().add(disposable);
    }

    @Override
    PagedMoviesResponse mergeCache(
            final PagedMoviesResponse cachedResp,
            final PagedMoviesResponse newResp) {
        cachedResp.getResults().addAll(
                newResp.getResults());
        cachedResp.setTotalResults(Math.max(
                cachedResp.getTotalResults(),
                newResp.getTotalResults()));
        cachedResp.setTotalPages(Math.max(
                cachedResp.getTotalPages(),
                newResp.getTotalPages()));
        cachedResp.setPage(newResp.getPage());
        return cachedResp;
    }

    @NonNull
    @Override
    String cacheKey(final MoviesReq req) {
        return keyGenerator.generateKey(req.status);
    }
}

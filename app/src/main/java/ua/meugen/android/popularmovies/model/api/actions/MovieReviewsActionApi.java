package ua.meugen.android.popularmovies.model.api.actions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import ua.meugen.android.popularmovies.model.api.ServerApi;
import ua.meugen.android.popularmovies.model.cache.Cache;
import ua.meugen.android.popularmovies.model.cache.KeyGenerator;
import ua.meugen.android.popularmovies.model.config.Config;
import ua.meugen.android.popularmovies.model.db.dao.ReviewsDao;
import ua.meugen.android.popularmovies.model.db.entity.ReviewItem;
import ua.meugen.android.popularmovies.model.db.execs.Executor;
import ua.meugen.android.popularmovies.model.db.execs.data.ReviewsData;
import ua.meugen.android.popularmovies.model.network.resp.PagedReviewsResponse;

/**
 * Created by meugen on 15.11.2017.
 */

public class MovieReviewsActionApi extends OfflineFirstActionApi<Integer, List<ReviewItem>> {

    @Inject Cache cache;
    @Inject Config config;
    @Inject ReviewsDao reviewsDao;
    @Inject ServerApi serverApi;
    @Inject Executor<ReviewsData> mergeReviewsExecutor;
    @Inject KeyGenerator<Integer> keyGenerator;

    @Inject
    MovieReviewsActionApi() {}

    @NonNull
    @Override
    Single<List<ReviewItem>> offlineData(final Integer movieId) {
        return Single.fromCallable(() -> reviewsDao.byMovieId(movieId))
                .flatMap(this::checkEmpty);
    }

    private Single<List<ReviewItem>> checkEmpty(final List<ReviewItem> reviews) {
        if (reviews.isEmpty()) {
            throw new IllegalArgumentException("Offline data is empty.");
        }
        return Single.just(reviews);
    }

    @NonNull
    @Override
    Single<List<ReviewItem>> networkData(final Integer movieId) {
        return serverApi.getMovieReviews(movieId, config.getLanguage())
                .map(this::checkResponse);
    }

    private List<ReviewItem> checkResponse(final PagedReviewsResponse response) {
        if (!response.isSuccess()) {
            throw new IllegalArgumentException("Not success: code = "
                    + response.getStatusCode() + ", message = " + response.getStatusMessage());
        }
        return response.getResults();
    }

    @Override
    void storeOffline(final Integer movieId, final List<ReviewItem> reviews) {
        final Disposable disposable = mergeReviewsExecutor
                .executeTransaction(new ReviewsData(reviews, movieId))
                .subscribe();
        getCompositeDisposable().add(disposable);
    }

    @Override
    public void clearCache(final Integer movieId) {
        cache.clear(keyGenerator.generateKey(movieId));
    }

    @NonNull
    @Override
    List<ReviewItem> retrieveCache(final Integer movieId) {
        List<ReviewItem> reviews = cache.get(keyGenerator.generateKey(movieId));
        if (reviews == null) {
            throw new IllegalArgumentException("No cache found for reviews for movie with id" + movieId);
        }
        return reviews;
    }

    @Override
    void storeCache(final Integer movieId, final List<ReviewItem> reviews) {
        cache.set(keyGenerator.generateKey(movieId), reviews);
    }
}

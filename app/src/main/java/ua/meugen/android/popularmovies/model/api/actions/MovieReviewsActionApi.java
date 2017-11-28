package ua.meugen.android.popularmovies.model.api.actions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import ua.meugen.android.popularmovies.model.api.ServerApi;
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
        return Single.fromCallable(() -> reviewsDao.byMovieId(movieId));
    }

    @Nullable
    @Override
    Single<List<ReviewItem>> networkData(final Integer movieId) {
        return serverApi.getMovieReviews(movieId, config.getLanguage()).map(PagedReviewsResponse::getResults);
    }

    @Override
    void storeOffline(final Integer movieId, final List<ReviewItem> reviews) {
        final Disposable disposable = mergeReviewsExecutor
                .executeTransaction(new ReviewsData(reviews, movieId))
                .subscribe();
        getCompositeDisposable().add(disposable);
    }

    @NonNull
    @Override
    String cacheKey(final Integer movieId) {
        return keyGenerator.generateKey(movieId);
    }
}

package ua.meugen.android.popularmovies.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import java.util.List;
import java.util.Observable;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import ua.meugen.android.popularmovies.model.api.ModelApi;
import ua.meugen.android.popularmovies.model.responses.PagedReviewsDto;
import ua.meugen.android.popularmovies.model.responses.ReviewItemDto;
import ua.meugen.android.popularmovies.presenter.listeners.OnClickReviewListener;
import ua.meugen.android.popularmovies.view.adapters.ReviewsAdapter;
import ua.meugen.android.popularmovies.view.dialogs.ReviewDetailDialog;

/**
 * @author meugen
 */

public class MovieReviewsViewModel extends Observable implements OnClickReviewListener {

    private static final String TAG = MovieReviewsViewModel.class.getSimpleName();

    private static final String PARAM_MOVIE_ID = "movieId";

    public static void bindMovieId(final Bundle args, final int movieId) {
        args.putInt(PARAM_MOVIE_ID, movieId);
    }

    public final ReviewsAdapter adapter;

    private final ModelApi modelApi;
    private final CompositeSubscription compositeSubscription;

    private int movieId;

    @Inject
    public MovieReviewsViewModel(
            final Context context,
            final ModelApi modelApi) {
        this.modelApi = modelApi;
        compositeSubscription = new CompositeSubscription();
        adapter = new ReviewsAdapter(context, this);
    }

    public void restoreInstanceState(final Bundle state) {
        movieId = state.getInt(PARAM_MOVIE_ID);
    }

    public void saveInstanceState(final Bundle outState) {
        outState.putInt(PARAM_MOVIE_ID, movieId);
    }

    public void load() {
        Subscription subscription = modelApi
                .getMovieReviews(movieId)
                .map(PagedReviewsDto::getResults)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onReviewsSuccess, this::onReviewsError);
        compositeSubscription.add(subscription);
    }

    private void onReviewsSuccess(final List<ReviewItemDto> reviews) {
        adapter.setReviews(reviews);
    }

    private void onReviewsError(final Throwable th) {
        Log.e(TAG, th.getMessage(), th);
    }

    public void reset() {
        if (!compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
        }
    }

    @Override
    public void onClickReview(final ReviewItemDto dto) {
        setChanged();
        notifyObservers(dto);
    }

    public void showReview(final FragmentManager fm, final ReviewItemDto review) {
        final ReviewDetailDialog dialog = ReviewDetailDialog.newInstance(review);
        dialog.show(fm, "review_detail");
    }
}

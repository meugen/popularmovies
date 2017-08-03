package ua.meugen.android.popularmovies.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;
import ua.meugen.android.popularmovies.model.responses.PagedReviewsDto;
import ua.meugen.android.popularmovies.presenter.api.ModelApi;
import ua.meugen.android.popularmovies.ui.MovieReviewsView;

/**
 * @author meugen
 */

public class MovieReviewsPresenter implements MvpPresenter<MovieReviewsView> {

    private final ModelApi modelApi;

    private CompositeSubscription compositeSubscription;

    private MovieReviewsView view;
    private int movieId;

    @Inject
    public MovieReviewsPresenter(final ModelApi modelApi) {
        this.modelApi = modelApi;
    }

    @Override
    public void attachView(final MovieReviewsView view) {
        this.view = view;
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView(final boolean retainInstance) {
        this.view = null;
        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
            compositeSubscription = null;
        }
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(final int movieId) {
        this.movieId = movieId;
    }

    public void load() {
        Subscription subscription = modelApi
                .getMovieReviews(movieId)
                .map(PagedReviewsDto::getResults)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::onReviewsLoaded, this::onReviewsError);
        compositeSubscription.add(subscription);
    }

    private void onReviewsError(final Throwable th) {
        Timber.e(th.getMessage(), th);
    }
}

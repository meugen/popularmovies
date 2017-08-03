package ua.meugen.android.popularmovies.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;
import ua.meugen.android.popularmovies.model.responses.VideosDto;
import ua.meugen.android.popularmovies.presenter.api.ModelApi;
import ua.meugen.android.popularmovies.ui.MovieVideosView;

public class MovieVideosPresenter implements MvpPresenter<MovieVideosView> {

    private final ModelApi modelApi;

    private MovieVideosView view;
    private CompositeSubscription compositeSubscription;

    private int movieId;

    @Inject
    public MovieVideosPresenter(
            final ModelApi modelApi) {
        this.modelApi = modelApi;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(final int movieId) {
        this.movieId = movieId;
    }

    @Override
    public void attachView(final MovieVideosView view) {
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

    public void load() {
        Subscription subscription = modelApi.getMovieVideos(movieId)
                .map(VideosDto::getResults)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::onVideosLoaded, this::onVideosError);
        compositeSubscription.add(subscription);
    }

    private void onVideosError(final Throwable th) {
        Timber.e(th.getMessage(), th);
    }
}

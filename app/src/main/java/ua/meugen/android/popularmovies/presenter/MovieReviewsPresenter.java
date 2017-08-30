package ua.meugen.android.popularmovies.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import ua.meugen.android.popularmovies.model.responses.PagedReviewsDto;
import ua.meugen.android.popularmovies.presenter.api.ModelApi;
import ua.meugen.android.popularmovies.ui.MovieReviewsView;

/**
 * @author meugen
 */

public class MovieReviewsPresenter implements MvpPresenter<MovieReviewsView> {

    private final ModelApi modelApi;

    private CompositeDisposable compositeDisposable;

    private MovieReviewsView view;
    private int movieId;

    @Inject
    public MovieReviewsPresenter(final ModelApi modelApi) {
        this.modelApi = modelApi;
    }

    @Override
    public void attachView(final MovieReviewsView view) {
        this.view = view;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void detachView(final boolean retainInstance) {
        this.view = null;
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
            compositeDisposable = null;
        }
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(final int movieId) {
        this.movieId = movieId;
    }

    public void load() {
        Disposable disposable = modelApi
                .getMovieReviews(movieId)
                .map(PagedReviewsDto::getResults)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::onReviewsLoaded, this::onReviewsError);
        compositeDisposable.add(disposable);
    }

    private void onReviewsError(final Throwable th) {
        Timber.e(th.getMessage(), th);
    }
}

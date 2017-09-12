package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.reviews.presenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import ua.meugen.android.popularmovies.app.api.ModelApi;
import ua.meugen.android.popularmovies.model.responses.PagedReviewsDto;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.presenter.BaseMvpPresenter;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.reviews.state.MovieReviewsState;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.reviews.view.MovieReviewsView;

/**
 * @author meugen
 */

public class MovieReviewsPresenterImpl extends BaseMvpPresenter<MovieReviewsView, MovieReviewsState>
        implements MovieReviewsPresenter {

    private final ModelApi modelApi;

    private int movieId;

    @Inject
    public MovieReviewsPresenterImpl(final ModelApi modelApi) {
        this.modelApi = modelApi;
    }

    @Override
    public void onCreate(final MovieReviewsState state) {
        super.onCreate(state);
        movieId = state.getMovieId();
    }

    @Override
    public void onSaveInstanceState(final MovieReviewsState state) {
        super.onSaveInstanceState(state);
        state.setMovieId(movieId);
    }

    @Override
    public void onStart() {
        super.onStart();
        load();
    }

    public void load() {
        Disposable disposable = modelApi
                .getMovieReviews(movieId)
                .map(PagedReviewsDto::getResults)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::onReviewsLoaded, this::onReviewsError);
        getCompositeDisposable().add(disposable);
    }

    private void onReviewsError(final Throwable th) {
        Timber.e(th.getMessage(), th);
    }
}

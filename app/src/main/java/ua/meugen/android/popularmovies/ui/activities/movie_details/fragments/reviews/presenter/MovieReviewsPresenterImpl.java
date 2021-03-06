package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.reviews.presenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import timber.log.Timber;
import ua.meugen.android.popularmovies.model.api.AppCachedActionApi;
import ua.meugen.android.popularmovies.model.db.entity.ReviewItem;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.presenter.BaseMvpPresenter;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.reviews.state.MovieReviewsState;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.reviews.view.MovieReviewsView;
import ua.meugen.android.popularmovies.ui.rxloader.LifecycleHandler;

/**
 * @author meugen
 */

public class MovieReviewsPresenterImpl extends BaseMvpPresenter<MovieReviewsView, MovieReviewsState>
        implements MovieReviewsPresenter {

    private static final int LOADER_ID = 1;

    @Inject AppCachedActionApi<Integer, List<ReviewItem>> reviewsActionApi;
    @Inject LifecycleHandler lifecycleHandler;

    private int movieId;

    @Inject
    MovieReviewsPresenterImpl() {}

    @Override
    public void restoreState(final MovieReviewsState state) {
        super.restoreState(state);
        movieId = state.getMovieId();
    }

    @Override
    public void saveState(final MovieReviewsState state) {
        super.saveState(state);
        state.setMovieId(movieId);
    }

    public void load() {
        Disposable disposable = reviewsActionApi
                .action(movieId)
                .compose(lifecycleHandler.load(LOADER_ID))
                .subscribe(view::onReviewsLoaded, this::onReviewsError);
        getCompositeDisposable().add(disposable);
    }

    private void onReviewsError(final Throwable th) {
        Timber.e(th.getMessage(), th);
    }
}

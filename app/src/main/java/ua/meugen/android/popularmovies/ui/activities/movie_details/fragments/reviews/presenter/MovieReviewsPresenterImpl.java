package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.reviews.presenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import ua.meugen.android.popularmovies.model.api.ModelApi;
import ua.meugen.android.popularmovies.model.db.dao.ReviewsDao;
import ua.meugen.android.popularmovies.model.db.entity.ReviewItem;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.presenter.BaseMvpPresenter;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.reviews.state.MovieReviewsState;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.reviews.view.MovieReviewsView;

/**
 * @author meugen
 */

public class MovieReviewsPresenterImpl extends BaseMvpPresenter<MovieReviewsView, MovieReviewsState>
        implements MovieReviewsPresenter {

    @Inject ModelApi modelApi;
    @Inject ReviewsDao reviewsDao;

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
        Disposable disposable = modelApi
                .getMovieReviews(movieId)
                .map(response -> response.results)
                .flatMap(this::storeToDb)
                .onErrorResumeNext(loadFromDb())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::onReviewsLoaded, this::onReviewsError);
        getCompositeDisposable().add(disposable);
    }

    private void onReviewsError(final Throwable th) {
        Timber.e(th.getMessage(), th);
    }

    private Observable<List<ReviewItem>> storeToDb(final List<ReviewItem> reviews) {
        reviewsDao.merge(reviews);
        return Observable.just(reviews);
    }

    private Observable<List<ReviewItem>> loadFromDb() {
        return Observable.fromCallable(() -> reviewsDao.byMovieId(movieId));
    }
}

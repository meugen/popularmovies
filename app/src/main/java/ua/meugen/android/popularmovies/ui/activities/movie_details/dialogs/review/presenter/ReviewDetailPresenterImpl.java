package ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.review.presenter;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ua.meugen.android.popularmovies.model.db.dao.ReviewsDao;
import ua.meugen.android.popularmovies.model.db.entity.ReviewItem;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.presenter.BaseMvpPresenter;
import ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.review.state.ReviewDetailState;
import ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.review.view.ReviewDetailView;

/**
 * Created by meugen on 14.11.2017.
 */

public class ReviewDetailPresenterImpl extends BaseMvpPresenter<ReviewDetailView, ReviewDetailState>
        implements ReviewDetailPresenter {

    @Inject ReviewsDao reviewsDao;

    private String reviewId;
    private ReviewItem review;

    @Inject
    ReviewDetailPresenterImpl() {}

    @Override
    public void restoreState(final ReviewDetailState state) {
        super.restoreState(state);
        reviewId = state.getReviewId();
    }

    @Override
    public void saveState(final ReviewDetailState state) {
        super.saveState(state);
        state.setReviewId(reviewId);
    }

    @Override
    public void load() {
        final Single<ReviewItem> single = Single.create(emitter -> {
            ReviewItem review = reviewsDao.byId(reviewId);
            if (!emitter.isDisposed()) {
                emitter.onSuccess(review);
            }
        });
        final Disposable disposable = single
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onReviewLoaded);
        getCompositeDisposable().add(disposable);
    }

    private void onReviewLoaded(final ReviewItem review) {
        this.review = review;
        view.onReviewLoaded(review);
    }

    @Override
    public ReviewItem getReview() {
        return review;
    }
}

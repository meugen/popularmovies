package ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.review.presenter;

import ua.meugen.android.popularmovies.model.db.entity.ReviewItem;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.presenter.MvpPresenter;
import ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.review.state.ReviewDetailState;

/**
 * Created by meugen on 14.11.2017.
 */

public interface ReviewDetailPresenter extends MvpPresenter<ReviewDetailState> {

    void load();

    ReviewItem getReview();
}

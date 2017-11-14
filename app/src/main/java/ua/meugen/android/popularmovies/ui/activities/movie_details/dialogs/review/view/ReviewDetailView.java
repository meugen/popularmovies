package ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.review.view;

import ua.meugen.android.popularmovies.model.db.entity.ReviewItem;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.view.MvpView;

/**
 * Created by meugen on 14.11.2017.
 */

public interface ReviewDetailView extends MvpView {

    void onReviewLoaded(ReviewItem review);
}

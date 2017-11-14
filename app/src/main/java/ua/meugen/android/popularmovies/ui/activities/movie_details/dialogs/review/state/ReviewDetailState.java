package ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.review.state;

import ua.meugen.android.popularmovies.ui.activities.base.fragment.state.MvpState;

/**
 * Created by meugen on 14.11.2017.
 */

public interface ReviewDetailState extends MvpState {

    String PARAM_REVIEW_ID = "reviewId";

    String getReviewId();

    void setReviewId(String value);
}

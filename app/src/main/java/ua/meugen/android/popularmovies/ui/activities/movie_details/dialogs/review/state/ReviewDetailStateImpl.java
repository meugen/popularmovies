package ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.review.state;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.ui.activities.base.fragment.state.BaseMvpState;

/**
 * Created by meugen on 14.11.2017.
 */

public class ReviewDetailStateImpl extends BaseMvpState implements ReviewDetailState {

    @Inject
    ReviewDetailStateImpl() {}

    @Override
    public String getReviewId() {
        return bundle.getString(PARAM_REVIEW_ID);
    }

    @Override
    public void setReviewId(final String value) {
        bundle.putString(PARAM_REVIEW_ID, value);
    }
}

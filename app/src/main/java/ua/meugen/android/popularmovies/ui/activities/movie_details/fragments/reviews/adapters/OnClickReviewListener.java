package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.reviews.adapters;

import ua.meugen.android.popularmovies.model.db.entity.ReviewItem;

/**
 * @author meugen
 */
public interface OnClickReviewListener {

    void onClickReview(ReviewItem dto);
}

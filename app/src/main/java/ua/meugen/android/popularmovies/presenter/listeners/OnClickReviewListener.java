package ua.meugen.android.popularmovies.presenter.listeners;

import ua.meugen.android.popularmovies.model.responses.ReviewItemDto;

/**
 * @author meugen
 */
public interface OnClickReviewListener {

    void onClickReview(ReviewItemDto dto);
}
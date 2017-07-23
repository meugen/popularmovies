package ua.meugen.android.popularmovies.viewmodel;

import android.databinding.ObservableField;
import android.view.View;

import ua.meugen.android.popularmovies.model.dto.ReviewItemDto;
import ua.meugen.android.popularmovies.viewmodel.listeners.OnClickReviewListener;

/**
 * @author meugen
 */

public class MovieReviewItemViewModel implements View.OnClickListener {

    public final ObservableField<ReviewItemDto> review;
    private final OnClickReviewListener listener;

    public MovieReviewItemViewModel(
            final ReviewItemDto review,
            final OnClickReviewListener listener) {
        this.review = new ObservableField<>(review);
        this.listener = listener;
    }

    public void setReview(final ReviewItemDto review) {
        this.review.set(review);
    }

    @Override
    public void onClick(final View view) {
        if (listener != null) {
            listener.onClickReview(review.get());
        }
    }
}

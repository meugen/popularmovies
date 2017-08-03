package ua.meugen.android.popularmovies.ui;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

import ua.meugen.android.popularmovies.model.responses.ReviewItemDto;


public interface MovieReviewsView extends MvpView {

    void onReviewsLoaded(List<ReviewItemDto> reviews);
}

package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.reviews.view;

import java.util.List;

import ua.meugen.android.popularmovies.model.responses.ReviewItemDto;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.view.MvpView;

/**
 * Created by meugen on 11.09.17.
 */

public interface MovieReviewsView extends MvpView {

    void onReviewsLoaded(List<ReviewItemDto> reviews);
}

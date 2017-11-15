package ua.meugen.android.popularmovies.model.db.execs.data;

import java.util.List;

import ua.meugen.android.popularmovies.model.db.entity.ReviewItem;

/**
 * Created by meugen on 15.11.2017.
 */

public class ReviewsData {

    public final List<ReviewItem> reviews;
    public final int movieId;

    public ReviewsData(final List<ReviewItem> reviews, final int movieId) {
        this.reviews = reviews;
        this.movieId = movieId;
    }
}

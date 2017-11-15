package ua.meugen.android.popularmovies.model.db.execs;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.model.db.dao.ReviewsDao;
import ua.meugen.android.popularmovies.model.db.entity.ReviewItem;
import ua.meugen.android.popularmovies.model.db.execs.data.ReviewsData;

/**
 * Created by meugen on 15.11.2017.
 */

public class MergeReviewsExecutor extends AbstractExecutor<ReviewsData> {

    @Inject ReviewsDao reviewsDao;

    @Inject
    MergeReviewsExecutor() {}

    @Override
    protected void execute(final ReviewsData data) {
        for (ReviewItem review : data.reviews) {
            review.movieId = data.movieId;
        }
        reviewsDao.merge(data.reviews);
    }
}

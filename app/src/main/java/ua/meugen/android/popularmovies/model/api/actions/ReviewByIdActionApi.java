package ua.meugen.android.popularmovies.model.api.actions;

import javax.inject.Inject;

import io.reactivex.Observable;
import ua.meugen.android.popularmovies.model.api.AppActionApi;
import ua.meugen.android.popularmovies.model.db.dao.ReviewsDao;
import ua.meugen.android.popularmovies.model.db.entity.ReviewItem;

/**
 * Created by meugen on 15.11.2017.
 */

public class ReviewByIdActionApi implements AppActionApi<String, ReviewItem> {

    @Inject ReviewsDao reviewsDao;

    @Inject
    ReviewByIdActionApi() {}

    @Override
    public Observable<ReviewItem> action(final String id) {
        return Observable.fromCallable(() -> reviewsDao.byId(id));
    }
}

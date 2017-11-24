package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.reviews;

import android.support.v4.app.Fragment;

import java.util.List;

import dagger.Binds;
import dagger.Module;
import ua.meugen.android.popularmovies.app.di.PerFragment;
import ua.meugen.android.popularmovies.model.api.AppActionApi;
import ua.meugen.android.popularmovies.model.api.AppCachedActionApi;
import ua.meugen.android.popularmovies.model.api.actions.MovieReviewsActionApi;
import ua.meugen.android.popularmovies.model.db.entity.ReviewItem;
import ua.meugen.android.popularmovies.model.db.execs.Executor;
import ua.meugen.android.popularmovies.model.db.execs.MergeReviewsExecutor;
import ua.meugen.android.popularmovies.model.db.execs.data.ReviewsData;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.BaseFragmentModule;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.reviews.adapters.OnClickReviewListener;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.reviews.presenter.MovieReviewsPresenter;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.reviews.presenter.MovieReviewsPresenterImpl;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.reviews.state.MovieReviewsState;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.reviews.state.MovieReviewsStateImpl;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.reviews.view.MovieReviewsView;

/**
 * Created by meugen on 11.09.17.
 */
@Module(includes = BaseFragmentModule.class)
public abstract class MovieReviewsFragmentModule {

    @Binds @PerFragment
    abstract MovieReviewsPresenter bindPresenter(final MovieReviewsPresenterImpl impl);

    @Binds @PerFragment
    abstract MovieReviewsState bindState(final MovieReviewsStateImpl impl);

    @Binds @PerFragment
    abstract MovieReviewsView bindView(final MovieReviewsFragment fragment);

    @Binds @PerFragment
    abstract Fragment bindFragment(final MovieReviewsFragment fragment);

    @Binds @PerFragment
    abstract OnClickReviewListener bindClickReviewListener(final MovieReviewsFragment fragment);

    @Binds @PerFragment
    abstract AppCachedActionApi<Integer, List<ReviewItem>> bindReviewsActionApi(final MovieReviewsActionApi api);

    @Binds @PerFragment
    abstract Executor<ReviewsData> bindMergeReviewsExecutor(final MergeReviewsExecutor executor);
}

package ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.review;

import android.support.v4.app.Fragment;

import dagger.Binds;
import dagger.Module;
import ua.meugen.android.popularmovies.app.di.PerDialog;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.BaseFragmentModule;
import ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.review.presenter.ReviewDetailPresenter;
import ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.review.presenter.ReviewDetailPresenterImpl;
import ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.review.state.ReviewDetailState;
import ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.review.state.ReviewDetailStateImpl;
import ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.review.view.ReviewDetailView;

/**
 * Created by meugen on 14.11.2017.
 */

@Module(includes = BaseFragmentModule.class)
public abstract class ReviewDetailDialogModule {

    @Binds @PerDialog
    abstract Fragment bindFragment(final ReviewDetailDialog dialog);

    @Binds @PerDialog
    abstract ReviewDetailPresenter bindPresenter(final ReviewDetailPresenterImpl impl);

    @Binds @PerDialog
    abstract ReviewDetailState bindState(final ReviewDetailStateImpl impl);

    @Binds @PerDialog
    abstract ReviewDetailView bindView(final ReviewDetailDialog dialog);
}

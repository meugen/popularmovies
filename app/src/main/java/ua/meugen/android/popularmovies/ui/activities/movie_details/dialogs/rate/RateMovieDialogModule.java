package ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.rate;

import android.support.v4.app.Fragment;

import dagger.Binds;
import dagger.Module;
import ua.meugen.android.popularmovies.app.di.PerDialog;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.BaseFragmentModule;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.view.MvpView;
import ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.rate.presenter.RateMoviePresenter;
import ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.rate.presenter.RateMoviePresenterImpl;
import ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.rate.state.RateMovieState;
import ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.rate.state.RateMovieStateImpl;

/**
 * Created by meugen on 14.11.2017.
 */
@Module(includes = BaseFragmentModule.class)
public abstract class RateMovieDialogModule {

    @Binds @PerDialog
    abstract Fragment bindFragment(final RateMovieDialog dialog);

    @Binds @PerDialog
    abstract RateMoviePresenter bindPresenter(final RateMoviePresenterImpl impl);

    @Binds @PerDialog
    abstract RateMovieState bindState(final RateMovieStateImpl impl);

    @Binds @PerDialog
    abstract MvpView bindView(final RateMovieDialog dialog);
}

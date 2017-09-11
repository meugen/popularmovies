package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details;

import android.support.v4.app.Fragment;

import dagger.Binds;
import dagger.Module;
import ua.meugen.android.popularmovies.app.di.PerFragment;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.BaseFragmentModule;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details.presenter.MovieDetailsPresenter;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details.presenter.MovieDetailsPresenterImpl;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details.state.MovieDetailsState;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details.state.MovieDetailsStateImpl;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details.view.MovieDetailsView;

/**
 * @author meugen
 */
@Module(includes = BaseFragmentModule.class)
public abstract class MovieDetailsFragmentModule {

    @Binds @PerFragment
    public abstract MovieDetailsPresenter bindPresenter(final MovieDetailsPresenterImpl impl);

    @Binds @PerFragment
    public abstract MovieDetailsState bindState(final MovieDetailsStateImpl impl);

    @Binds @PerFragment
    public abstract MovieDetailsView bindView(final MovieDetailsFragment fragment);

    @Binds @PerFragment
    public abstract Fragment bindFragment(final MovieDetailsFragment fragment);
}

package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details;

import android.support.v4.app.Fragment;

import org.javatuples.Pair;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import ua.meugen.android.popularmovies.app.di.PerFragment;
import ua.meugen.android.popularmovies.model.api.AppActionApi;
import ua.meugen.android.popularmovies.model.api.actions.MovieByIdActionApi;
import ua.meugen.android.popularmovies.model.api.actions.NewGuestSessionActionApi;
import ua.meugen.android.popularmovies.model.api.actions.RateMovieActionApi;
import ua.meugen.android.popularmovies.model.api.actions.SwitchFavoriteActionApi;
import ua.meugen.android.popularmovies.model.cache.KeyGenerator;
import ua.meugen.android.popularmovies.model.db.entity.MovieItem;
import ua.meugen.android.popularmovies.model.network.resp.BaseResponse;
import ua.meugen.android.popularmovies.model.network.resp.NewGuestSessionResponse;
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
    abstract MovieDetailsPresenter bindPresenter(final MovieDetailsPresenterImpl impl);

    @Binds @PerFragment
    abstract MovieDetailsState bindState(final MovieDetailsStateImpl impl);

    @Binds @PerFragment
    abstract MovieDetailsView bindView(final MovieDetailsFragment fragment);

    @Binds @PerFragment
    abstract Fragment bindFragment(final MovieDetailsFragment fragment);

    @Binds @PerFragment
    abstract AppActionApi<Integer, MovieItem> bindMovieByIdActionApi(final MovieByIdActionApi api);

    @Binds @PerFragment
    abstract AppActionApi<Pair<Integer, Float>, BaseResponse> bindRateMovieActionApi(
            final RateMovieActionApi api);

    @Binds @PerFragment
    abstract AppActionApi<Void, NewGuestSessionResponse> bindNewGuestSessionActionApi(
            final NewGuestSessionActionApi api);

    @Provides @PerFragment
    static KeyGenerator<Integer> provideMoviesKeyGenerator() {
        return KeyGenerator.forMovies();
    }

    @Binds @PerFragment
    abstract AppActionApi<MovieItem, Void> bindSwitchFavoriteActionApi(
            final SwitchFavoriteActionApi api);
}

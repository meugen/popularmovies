package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details.presenter;

import org.javatuples.Pair;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import ua.meugen.android.popularmovies.model.api.AppActionApi;
import ua.meugen.android.popularmovies.model.db.dao.MoviesDao;
import ua.meugen.android.popularmovies.model.db.entity.MovieItem;
import ua.meugen.android.popularmovies.model.network.resp.BaseResponse;
import ua.meugen.android.popularmovies.model.network.resp.NewGuestSessionResponse;
import ua.meugen.android.popularmovies.model.session.SessionStorage;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details.state.MovieDetailsState;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details.view.MovieDetailsView;
import ua.meugen.android.popularmovies.ui.rxloader.LifecycleHandler;

/**
 * Created by meugen on 24.11.2017.
 */

public class MovieDetailsPresenterTest {

    private @Mock AppActionApi<Integer, MovieItem> movieByIdActionApi;
    private @Mock SessionStorage sessionStorage;
    private @Mock MoviesDao moviesDao;
    private @Mock AppActionApi<Pair<Integer, Float>, BaseResponse> rateMovieActionApi;
    private @Mock AppActionApi<Void, NewGuestSessionResponse> newGuestSessionActionApi;
    private @Mock LifecycleHandler lifecycleHandler;
    private @Mock MovieDetailsView view;
    private @Mock MovieDetailsState state;

    private MovieItem movie;
    private MovieDetailsPresenter presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        movie = Mockito.mock(MovieItem.class);

        MovieDetailsPresenterImpl presenter = new MovieDetailsPresenterImpl();
        presenter.movieByIdActionApi = movieByIdActionApi;
        presenter.sessionStorage = sessionStorage;
        presenter.moviesDao = moviesDao;
        presenter.rateMovieActionApi = rateMovieActionApi;
        presenter.newGuestSessionActionApi = newGuestSessionActionApi;
        presenter.lifecycleHandler = lifecycleHandler;
        presenter.view = view;
        this.presenter = presenter;
    }

    @Test
    public void testState() {
        Mockito.when(state.getMovieId()).thenReturn(1);

        presenter.restoreState(state);
        presenter.saveState(state);
        InOrder inOrder = Mockito.inOrder(state,
                movieByIdActionApi, sessionStorage, moviesDao,
                rateMovieActionApi, newGuestSessionActionApi,
                lifecycleHandler, view);
        inOrder.verify(state).getMovieId();
        inOrder.verify(state).setMovieId(1);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testLoadDefault() {
        Mockito.when(state.getMovieId()).thenReturn(1);
        Mockito.when(movieByIdActionApi.action(1))
                .thenReturn(Observable.just(movie));
        Mockito.when(lifecycleHandler.load(MovieDetailsPresenter.MOVIE_LOADER_ID))
                .thenReturn(upstream -> upstream);
        Mockito.when(lifecycleHandler.hasLoader(MovieDetailsPresenter.RATE_MOVIE_LOADER_ID))
                .thenReturn(false);
        Mockito.when(lifecycleHandler.hasLoader(MovieDetailsPresenter.GUEST_SESSION_LOADER_ID))
                .thenReturn(false);

        presenter.restoreState(state);
        presenter.load();
        InOrder inOrder = Mockito.inOrder(state,
                movieByIdActionApi, sessionStorage, moviesDao,
                rateMovieActionApi, newGuestSessionActionApi,
                lifecycleHandler, view);
        inOrder.verify(state).getMovieId();
        inOrder.verify(movieByIdActionApi).action(1);
        inOrder.verify(lifecycleHandler).load(MovieDetailsPresenter.MOVIE_LOADER_ID);
        inOrder.verify(view).gotMovie(movie);
        inOrder.verify(lifecycleHandler).hasLoader(MovieDetailsPresenter.RATE_MOVIE_LOADER_ID);
        inOrder.verify(lifecycleHandler).hasLoader(MovieDetailsPresenter.GUEST_SESSION_LOADER_ID);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testLoadRateMovie() {
        Mockito.when(state.getMovieId()).thenReturn(1);
        Mockito.when(movieByIdActionApi.action(1))
                .thenReturn(Observable.just(movie));
        Mockito.when(lifecycleHandler.load(MovieDetailsPresenter.MOVIE_LOADER_ID))
                .thenReturn(upstream -> upstream);
        Mockito.when(lifecycleHandler.hasLoader(MovieDetailsPresenter.RATE_MOVIE_LOADER_ID))
                .thenReturn(true);
        Mockito.when(lifecycleHandler.hasLoader(MovieDetailsPresenter.GUEST_SESSION_LOADER_ID))
                .thenReturn(false);

        presenter.restoreState(state);
        presenter.load();
        InOrder inOrder = Mockito.inOrder(state,
                movieByIdActionApi, sessionStorage, moviesDao,
                rateMovieActionApi, newGuestSessionActionApi,
                lifecycleHandler, view);
        inOrder.verify(state).getMovieId();
        inOrder.verify(movieByIdActionApi).action(1);
        inOrder.verify(lifecycleHandler).load(MovieDetailsPresenter.MOVIE_LOADER_ID);
        inOrder.verify(view).gotMovie(movie);
        inOrder.verify(lifecycleHandler).hasLoader(MovieDetailsPresenter.RATE_MOVIE_LOADER_ID);
        inOrder.verify(lifecycleHandler).next(MovieDetailsPresenter.RATE_MOVIE_LOADER_ID);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testLoadGuestSession() {
        Mockito.when(state.getMovieId()).thenReturn(1);
        Mockito.when(movieByIdActionApi.action(1))
                .thenReturn(Observable.just(movie));
        Mockito.when(lifecycleHandler.load(MovieDetailsPresenter.MOVIE_LOADER_ID))
                .thenReturn(upstream -> upstream);
        Mockito.when(lifecycleHandler.hasLoader(MovieDetailsPresenter.RATE_MOVIE_LOADER_ID))
                .thenReturn(false);
        Mockito.when(lifecycleHandler.hasLoader(MovieDetailsPresenter.GUEST_SESSION_LOADER_ID))
                .thenReturn(true);

        presenter.restoreState(state);
        presenter.load();
        InOrder inOrder = Mockito.inOrder(state,
                movieByIdActionApi, sessionStorage, moviesDao,
                rateMovieActionApi, newGuestSessionActionApi,
                lifecycleHandler, view);
        inOrder.verify(state).getMovieId();
        inOrder.verify(movieByIdActionApi).action(1);
        inOrder.verify(lifecycleHandler).load(MovieDetailsPresenter.MOVIE_LOADER_ID);
        inOrder.verify(view).gotMovie(movie);
        inOrder.verify(lifecycleHandler).hasLoader(MovieDetailsPresenter.RATE_MOVIE_LOADER_ID);
        inOrder.verify(lifecycleHandler).hasLoader(MovieDetailsPresenter.GUEST_SESSION_LOADER_ID);
        inOrder.verify(lifecycleHandler).next(MovieDetailsPresenter.GUEST_SESSION_LOADER_ID);
        inOrder.verifyNoMoreInteractions();
    }
}

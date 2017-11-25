package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details.presenter;

import org.javatuples.Pair;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Date;

import io.reactivex.Observable;
import ua.meugen.android.popularmovies.model.api.AppActionApi;
import ua.meugen.android.popularmovies.model.db.dao.MoviesDao;
import ua.meugen.android.popularmovies.model.db.entity.MovieItem;
import ua.meugen.android.popularmovies.model.network.resp.BaseResponse;
import ua.meugen.android.popularmovies.model.network.resp.NewGuestSessionResponse;
import ua.meugen.android.popularmovies.model.session.Session;
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

    private @Mock Session session;
    private @Mock BaseResponse rateMovieResponse;
    private @Mock NewGuestSessionResponse newGuestSessionResponse;
    private @Mock MovieItem movie;
    private MovieDetailsPresenter presenter;
    private InOrder inOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        inOrder = Mockito.inOrder(state,
                movieByIdActionApi, sessionStorage, moviesDao,
                rateMovieActionApi, newGuestSessionActionApi,
                lifecycleHandler, view, rateMovieResponse,
                newGuestSessionResponse);

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
        inOrder.verify(state).getMovieId();
        inOrder.verify(state).setMovieId(1);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testLoad() {
        Mockito.when(state.getMovieId()).thenReturn(1);
        Mockito.when(movieByIdActionApi.action(1))
                .thenReturn(Observable.just(movie));
        Mockito.when(lifecycleHandler.load(MovieDetailsPresenter.MOVIE_LOADER_ID))
                .thenReturn(upstream -> upstream);

        presenter.restoreState(state);
        presenter.load();
        inOrder.verify(state).getMovieId();
        inOrder.verify(movieByIdActionApi).action(1);
        inOrder.verify(lifecycleHandler).load(MovieDetailsPresenter.MOVIE_LOADER_ID);
        inOrder.verify(view).gotMovie(movie);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testResumeNone() {
        Mockito.when(lifecycleHandler.hasLoader(MovieDetailsPresenter.RATE_MOVIE_LOADER_ID))
                .thenReturn(false);
        Mockito.when(lifecycleHandler.hasLoader(MovieDetailsPresenter.GUEST_SESSION_LOADER_ID))
                .thenReturn(false);

        presenter.resume();
        inOrder.verify(lifecycleHandler).hasLoader(MovieDetailsPresenter.RATE_MOVIE_LOADER_ID);
        inOrder.verify(lifecycleHandler).hasLoader(MovieDetailsPresenter.GUEST_SESSION_LOADER_ID);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testResumeRateMovieSuccess() {
        Mockito.when(lifecycleHandler.hasLoader(MovieDetailsPresenter.RATE_MOVIE_LOADER_ID))
                .thenReturn(true);
        Mockito.when(lifecycleHandler.hasLoader(MovieDetailsPresenter.GUEST_SESSION_LOADER_ID))
                .thenReturn(false);
        Mockito.when(rateMovieResponse.isSuccess()).thenReturn(true);
        Mockito.when(lifecycleHandler.next(MovieDetailsPresenter.RATE_MOVIE_LOADER_ID))
                .thenReturn(Observable.just(rateMovieResponse));

        presenter.resume();
        inOrder.verify(lifecycleHandler).hasLoader(MovieDetailsPresenter.RATE_MOVIE_LOADER_ID);
        inOrder.verify(lifecycleHandler).next(MovieDetailsPresenter.RATE_MOVIE_LOADER_ID);
        inOrder.verify(lifecycleHandler).clear(MovieDetailsPresenter.RATE_MOVIE_LOADER_ID);
        inOrder.verify(rateMovieResponse).isSuccess();
        inOrder.verify(view).onMovieRatedSuccess();
        inOrder.verify(lifecycleHandler).hasLoader(MovieDetailsPresenter.GUEST_SESSION_LOADER_ID);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testResumeRateMovieNotSuccess() {
        Mockito.when(lifecycleHandler.hasLoader(MovieDetailsPresenter.RATE_MOVIE_LOADER_ID))
                .thenReturn(true);
        Mockito.when(lifecycleHandler.hasLoader(MovieDetailsPresenter.GUEST_SESSION_LOADER_ID))
                .thenReturn(false);
        Mockito.when(rateMovieResponse.isSuccess()).thenReturn(false);
        Mockito.when(rateMovieResponse.getStatusMessage()).thenReturn("Some error");
        Mockito.when(lifecycleHandler.next(MovieDetailsPresenter.RATE_MOVIE_LOADER_ID))
                .thenReturn(Observable.just(rateMovieResponse));

        presenter.resume();
        inOrder.verify(lifecycleHandler).hasLoader(MovieDetailsPresenter.RATE_MOVIE_LOADER_ID);
        inOrder.verify(lifecycleHandler).next(MovieDetailsPresenter.RATE_MOVIE_LOADER_ID);
        inOrder.verify(lifecycleHandler).clear(MovieDetailsPresenter.RATE_MOVIE_LOADER_ID);
        inOrder.verify(rateMovieResponse).isSuccess();
        inOrder.verify(view).onServerError("Some error");
        inOrder.verify(lifecycleHandler).hasLoader(MovieDetailsPresenter.GUEST_SESSION_LOADER_ID);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testResumeRateMovieError() {
        Mockito.when(lifecycleHandler.hasLoader(MovieDetailsPresenter.RATE_MOVIE_LOADER_ID))
                .thenReturn(true);
        Mockito.when(lifecycleHandler.hasLoader(MovieDetailsPresenter.GUEST_SESSION_LOADER_ID))
                .thenReturn(false);
        Mockito.when(lifecycleHandler.next(MovieDetailsPresenter.RATE_MOVIE_LOADER_ID))
                .thenReturn(Observable.error(new Exception()));

        presenter.resume();
        inOrder.verify(lifecycleHandler).hasLoader(MovieDetailsPresenter.RATE_MOVIE_LOADER_ID);
        inOrder.verify(lifecycleHandler).next(MovieDetailsPresenter.RATE_MOVIE_LOADER_ID);
        inOrder.verify(lifecycleHandler).clear(MovieDetailsPresenter.RATE_MOVIE_LOADER_ID);
        inOrder.verify(view).onError();
        inOrder.verify(lifecycleHandler).hasLoader(MovieDetailsPresenter.GUEST_SESSION_LOADER_ID);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testResumeGuestSessionSuccess() {
        Mockito.when(lifecycleHandler.hasLoader(MovieDetailsPresenter.RATE_MOVIE_LOADER_ID))
                .thenReturn(false);
        Mockito.when(lifecycleHandler.hasLoader(MovieDetailsPresenter.GUEST_SESSION_LOADER_ID))
                .thenReturn(true);
        Mockito.when(newGuestSessionResponse.isSuccess()).thenReturn(true);
        Mockito.when(newGuestSessionResponse.getGuestSessionId())
                .thenReturn("da57de57-a66d-4b85-9b24-1cc4533ca77a");
        Mockito.when(newGuestSessionResponse.getExpiresAt())
                .thenReturn(new Date(1511611260745L));
        Mockito.when(lifecycleHandler.next(MovieDetailsPresenter.GUEST_SESSION_LOADER_ID))
                .thenReturn(Observable.just(newGuestSessionResponse));

        presenter.resume();
        inOrder.verify(lifecycleHandler).hasLoader(MovieDetailsPresenter.RATE_MOVIE_LOADER_ID);
        inOrder.verify(lifecycleHandler).hasLoader(MovieDetailsPresenter.GUEST_SESSION_LOADER_ID);
        inOrder.verify(lifecycleHandler).next(MovieDetailsPresenter.GUEST_SESSION_LOADER_ID);
        inOrder.verify(lifecycleHandler).clear(MovieDetailsPresenter.GUEST_SESSION_LOADER_ID);
        inOrder.verify(newGuestSessionResponse).isSuccess();
        inOrder.verify(newGuestSessionResponse).getGuestSessionId();
        inOrder.verify(newGuestSessionResponse).getExpiresAt();
        inOrder.verify(sessionStorage).storeSession(
                "da57de57-a66d-4b85-9b24-1cc4533ca77a",
                true, new Date(1511611260745L));
        inOrder.verify(view).rateMovieWithSession();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testResumeGuestSessionNotSuccess() {
        Mockito.when(lifecycleHandler.hasLoader(MovieDetailsPresenter.RATE_MOVIE_LOADER_ID))
                .thenReturn(false);
        Mockito.when(lifecycleHandler.hasLoader(MovieDetailsPresenter.GUEST_SESSION_LOADER_ID))
                .thenReturn(true);
        Mockito.when(newGuestSessionResponse.isSuccess()).thenReturn(false);
        Mockito.when(newGuestSessionResponse.getStatusMessage()).thenReturn("Some error");
        Mockito.when(lifecycleHandler.next(MovieDetailsPresenter.GUEST_SESSION_LOADER_ID))
                .thenReturn(Observable.just(newGuestSessionResponse));

        presenter.resume();
        inOrder.verify(lifecycleHandler).hasLoader(MovieDetailsPresenter.RATE_MOVIE_LOADER_ID);
        inOrder.verify(lifecycleHandler).hasLoader(MovieDetailsPresenter.GUEST_SESSION_LOADER_ID);
        inOrder.verify(lifecycleHandler).next(MovieDetailsPresenter.GUEST_SESSION_LOADER_ID);
        inOrder.verify(lifecycleHandler).clear(MovieDetailsPresenter.GUEST_SESSION_LOADER_ID);
        inOrder.verify(newGuestSessionResponse).isSuccess();
        inOrder.verify(view).onServerError("Some error");
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testResumeGuestSessionError() {
        Mockito.when(lifecycleHandler.hasLoader(MovieDetailsPresenter.RATE_MOVIE_LOADER_ID))
                .thenReturn(false);
        Mockito.when(lifecycleHandler.hasLoader(MovieDetailsPresenter.GUEST_SESSION_LOADER_ID))
                .thenReturn(true);
        Mockito.when(lifecycleHandler.next(MovieDetailsPresenter.GUEST_SESSION_LOADER_ID))
                .thenReturn(Observable.error(new Exception()));

        presenter.resume();
        inOrder.verify(lifecycleHandler).hasLoader(MovieDetailsPresenter.RATE_MOVIE_LOADER_ID);
        inOrder.verify(lifecycleHandler).hasLoader(MovieDetailsPresenter.GUEST_SESSION_LOADER_ID);
        inOrder.verify(lifecycleHandler).next(MovieDetailsPresenter.GUEST_SESSION_LOADER_ID);
        inOrder.verify(lifecycleHandler).clear(MovieDetailsPresenter.GUEST_SESSION_LOADER_ID);
        inOrder.verify(view).onError();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testRateMovieWithoutSession() {
        Mockito.when(sessionStorage.getSession()).thenReturn(null);

        presenter.rateMovie();
        inOrder.verify(view).selectSession();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testRateMovieWithSession() {
        Mockito.when(sessionStorage.getSession()).thenReturn(session);

        presenter.rateMovie();
        inOrder.verify(view).rateMovieWithSession();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testStoreUserSession() {
        presenter.storeUserSession("5d40f076-3194-4a70-b743-dbef9a436d1d");
        inOrder.verify(sessionStorage).storeSession(
                "5d40f076-3194-4a70-b743-dbef9a436d1d",
                false, new Date(Long.MAX_VALUE));
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testSwitchFavoritesMovieNull() {
        presenter.switchFavorites();
        inOrder.verifyNoMoreInteractions();
    }

//    @Test
//    public void testSwitchFavoritesMovieNotNUll() {
//        Mockito.when(state.getMovieId()).thenReturn(1);
//        Mockito.when(movieByIdActionApi.action(1))
//                .thenReturn(Observable.just(movie));
//        Mockito.when(lifecycleHandler.load(MovieDetailsPresenter.MOVIE_LOADER_ID))
//                .thenReturn(upstream -> upstream);
//
//        presenter.restoreState(state);
//        presenter.load();
//        presenter.switchFavorites();
//        inOrder.verify(state).getMovieId();
//        inOrder.verify(movieByIdActionApi).action(1);
//        inOrder.verify(lifecycleHandler).load(MovieDetailsPresenter.MOVIE_LOADER_ID);
//        inOrder.verify(view).gotMovie(movie);
//        inOrder.verify(moviesDao).merge(Collections.singleton(movie));
//        inOrder.verifyNoMoreInteractions();
//    }
}

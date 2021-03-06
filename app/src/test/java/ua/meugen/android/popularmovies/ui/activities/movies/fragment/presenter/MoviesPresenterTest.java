package ua.meugen.android.popularmovies.ui.activities.movies.fragment.presenter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import ua.meugen.android.popularmovies.model.SortType;
import ua.meugen.android.popularmovies.model.api.AppCachedActionApi;
import ua.meugen.android.popularmovies.model.api.req.MoviesReq;
import ua.meugen.android.popularmovies.model.db.entity.MovieItem;
import ua.meugen.android.popularmovies.model.prefs.PrefsStorage;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.state.MoviesState;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.view.MoviesView;
import ua.meugen.android.popularmovies.ui.rxloader.LifecycleHandler;

/**
 * Created by meugen on 24.11.2017.
 */

public class MoviesPresenterTest {

    private @Mock AppCachedActionApi<MoviesReq, List<MovieItem>> moviesActionApi;
    private @Mock LifecycleHandler lifecycleHandler;
    private @Mock PrefsStorage prefsStorage;
    private @Mock MoviesView view;
    private @Mock MoviesState state;

    private List<MovieItem> movies;
    private MoviesPresenter presenter;
    private InOrder inOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        movies = Arrays.asList(
                Mockito.mock(MovieItem.class), Mockito.mock(MovieItem.class),
                Mockito.mock(MovieItem.class), Mockito.mock(MovieItem.class));
        inOrder = Mockito.inOrder(prefsStorage,
                view, moviesActionApi, lifecycleHandler, state);

        MoviesPresenterImpl presenter = new MoviesPresenterImpl();
        presenter.moviesActionApi = moviesActionApi;
        presenter.lifecycleHandler = lifecycleHandler;
        presenter.prefsStorage = prefsStorage;
        presenter.view = view;
        this.presenter = presenter;
    }

    @Test
    public void testLoadPopular() {
        Mockito.when(prefsStorage.getSortType()).thenReturn(SortType.POPULAR);
        Mockito.when(moviesActionApi.action(new MoviesReq(SortType.POPULAR, 1)))
                .thenReturn(Observable.just(movies));
        Mockito.when(lifecycleHandler.load(MoviesPresenter.LOADER_ID, false))
                .thenReturn(upstream -> upstream);

        presenter.load();
        inOrder.verify(prefsStorage).getSortType();
        inOrder.verify(view).showRefreshing();
        inOrder.verify(moviesActionApi).action(new MoviesReq(SortType.POPULAR, 1));
        inOrder.verify(lifecycleHandler).load(MoviesPresenter.LOADER_ID, false);
        inOrder.verify(view).showNewMovies(movies);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testLoadTopRated() {
        Mockito.when(prefsStorage.getSortType()).thenReturn(SortType.TOP_RATED);
        Mockito.when(moviesActionApi.action(new MoviesReq(SortType.TOP_RATED, 1)))
                .thenReturn(Observable.just(movies));
        Mockito.when(lifecycleHandler.load(MoviesPresenter.LOADER_ID, false))
                .thenReturn(upstream -> upstream);

        presenter.load();
        inOrder.verify(prefsStorage).getSortType();
        inOrder.verify(view).showRefreshing();
        inOrder.verify(moviesActionApi).action(new MoviesReq(SortType.TOP_RATED, 1));
        inOrder.verify(lifecycleHandler).load(MoviesPresenter.LOADER_ID, false);
        inOrder.verify(view).showNewMovies(movies);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testRefreshPopular() {
        Mockito.when(moviesActionApi.action(new MoviesReq(SortType.POPULAR, 1)))
                .thenReturn(Observable.just(movies));
        Mockito.when(lifecycleHandler.load(MoviesPresenter.LOADER_ID, true))
                .thenReturn(upstream -> upstream);

        presenter.refresh(SortType.POPULAR);
        inOrder.verify(prefsStorage).setSortType(SortType.POPULAR);
        inOrder.verify(view).showRefreshing();
        inOrder.verify(moviesActionApi).action(new MoviesReq(SortType.POPULAR, 1));
        inOrder.verify(lifecycleHandler).load(MoviesPresenter.LOADER_ID, true);
        inOrder.verify(view).showNewMovies(movies);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testRefreshTopRated() {
        Mockito.when(moviesActionApi.action(new MoviesReq(SortType.TOP_RATED, 1)))
                .thenReturn(Observable.just(movies));
        Mockito.when(lifecycleHandler.load(MoviesPresenter.LOADER_ID, true))
                .thenReturn(upstream -> upstream);

        presenter.refresh(SortType.TOP_RATED);
        inOrder.verify(prefsStorage).setSortType(SortType.TOP_RATED);
        inOrder.verify(view).showRefreshing();
        inOrder.verify(moviesActionApi).action(new MoviesReq(SortType.TOP_RATED, 1));
        inOrder.verify(lifecycleHandler).load(MoviesPresenter.LOADER_ID, true);
        inOrder.verify(view).showNewMovies(movies);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testSortTypePopular() {
        Mockito.when(prefsStorage.getSortType()).thenReturn(SortType.POPULAR);

        int sortType = presenter.getSortType();
        inOrder.verify(prefsStorage).getSortType();
        inOrder.verifyNoMoreInteractions();

        Assert.assertEquals(sortType, SortType.POPULAR);
    }

    @Test
    public void testSortTypeTopRated() {
        Mockito.when(prefsStorage.getSortType()).thenReturn(SortType.TOP_RATED);

        int sortType = presenter.getSortType();
        inOrder.verify(prefsStorage).getSortType();
        inOrder.verifyNoMoreInteractions();

        Assert.assertEquals(sortType, SortType.TOP_RATED);
    }

    @Test
    public void testClearCachePopular() {
        Mockito.when(prefsStorage.getSortType()).thenReturn(SortType.POPULAR);

        presenter.clearCache();
        inOrder.verify(moviesActionApi).clearCache(new MoviesReq(SortType.POPULAR, 1));
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testClearCacheTopRated() {
        Mockito.when(prefsStorage.getSortType()).thenReturn(SortType.TOP_RATED);

        presenter.clearCache();
        inOrder.verify(moviesActionApi).clearCache(new MoviesReq(SortType.TOP_RATED, 1));
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testState() {
        Mockito.when(state.getPage()).thenReturn(1);

        presenter.restoreState(state);
        presenter.saveState(state);
        inOrder.verify(state).getPage();
        inOrder.verify(state).setPage(1);
        inOrder.verifyNoMoreInteractions();
    }
}

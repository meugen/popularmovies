package ua.meugen.android.popularmovies.ui.activities.movies.fragment.presenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import timber.log.Timber;
import ua.meugen.android.popularmovies.model.SortType;
import ua.meugen.android.popularmovies.model.api.AppCachedActionApi;
import ua.meugen.android.popularmovies.model.api.req.MoviesReq;
import ua.meugen.android.popularmovies.model.db.entity.MovieItem;
import ua.meugen.android.popularmovies.model.network.resp.PagedMoviesResponse;
import ua.meugen.android.popularmovies.model.prefs.PrefsStorage;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.presenter.BaseMvpPresenter;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.state.MoviesState;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.view.MoviesView;
import ua.meugen.android.popularmovies.ui.rxloader.LifecycleHandler;

/**
 * @author meugen
 */

public class MoviesPresenterImpl extends BaseMvpPresenter<MoviesView, MoviesState>
        implements MoviesPresenter {

    @Inject AppCachedActionApi<MoviesReq, PagedMoviesResponse> moviesActionApi;
    @Inject LifecycleHandler lifecycleHandler;
    @Inject PrefsStorage prefsStorage;

    @Inject
    MoviesPresenterImpl() {}

    @Override
    public void load() {
        _load(false, prefsStorage.getSortType());
    }

    @Override
    public void refresh(
            @SortType final int sortType) {
        prefsStorage.setSortType(sortType);
        _load(true, sortType);
    }

    private void _load(
            final boolean restart,
            @SortType final int sortType) {
        view.showRefreshing();

        final Disposable disposable = moviesActionApi
                .action(new MoviesReq(sortType, 1))
                .compose(lifecycleHandler.load(LOADER_ID, restart))
                .subscribe(view::showMovies, this::onError);
        getCompositeDisposable().add(disposable);
    }

    @Override
    public void loadNextPage(final int page) {
        final Disposable disposable = moviesActionApi
                .action(new MoviesReq(PAGE_LOADER_ID, page))
                .compose(lifecycleHandler.reload(PAGE_LOADER_ID))
                .subscribe(view::showNextPage, this::onError);
        getCompositeDisposable().add(disposable);
    }

    @Override
    public int getSortType() {
        return prefsStorage.getSortType();
    }

    @Override
    public void clearCache() {
        moviesActionApi.clearCache(new MoviesReq(prefsStorage.getSortType(), 1));
    }

    private void onError(final Throwable th) {
        Timber.e(th.getMessage(), th);
    }
}

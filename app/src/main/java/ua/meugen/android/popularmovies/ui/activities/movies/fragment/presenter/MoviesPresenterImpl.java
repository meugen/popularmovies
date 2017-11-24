package ua.meugen.android.popularmovies.ui.activities.movies.fragment.presenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import ua.meugen.android.popularmovies.model.SortType;
import ua.meugen.android.popularmovies.model.api.AppActionApi;
import ua.meugen.android.popularmovies.model.db.dao.MoviesDao;
import ua.meugen.android.popularmovies.model.db.entity.MovieItem;
import ua.meugen.android.popularmovies.model.db.execs.Executor;
import ua.meugen.android.popularmovies.model.db.execs.data.MoviesData;
import ua.meugen.android.popularmovies.model.prefs.PrefsStorage;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.presenter.BaseMvpPresenter;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.state.MoviesState;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.view.MoviesView;
import ua.meugen.android.popularmovies.ui.rxloader.LifecycleHandler;
import ua.meugen.android.popularmovies.ui.utils.RxUtils;

/**
 * @author meugen
 */

public class MoviesPresenterImpl extends BaseMvpPresenter<MoviesView, MoviesState>
        implements MoviesPresenter {

    private static final int LOADER_ID = 1;

    @Inject AppActionApi<Integer, List<MovieItem>> moviesActionApi;
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
                .action(sortType)
                .compose(RxUtils.async())
                .compose(lifecycleHandler.load(LOADER_ID, restart))
                .subscribe(view::showMovies, this::onError);
        getCompositeDisposable().add(disposable);
    }

    @Override
    public int getSortType() {
        return prefsStorage.getSortType();
    }

    private void onError(final Throwable th) {
        Timber.e(th.getMessage(), th);
    }
}

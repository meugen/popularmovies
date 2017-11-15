package ua.meugen.android.popularmovies.ui.activities.movies.fragment.presenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
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
import ua.meugen.android.popularmovies.ui.activities.base.fragment.presenter.BaseMvpPresenter;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.state.MoviesState;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.view.MoviesView;

/**
 * @author meugen
 */

public class MoviesPresenterImpl extends BaseMvpPresenter<MoviesView, MoviesState>
        implements MoviesPresenter {

    @Inject AppActionApi<Integer, List<MovieItem>> moviesActionApi;

    @SortType
    private int sortType;

    @Inject
    MoviesPresenterImpl() {}

    @Override
    public void restoreState(final MoviesState state) {
        super.restoreState(state);
        sortType = state.getSortType();
    }

    @Override
    public void saveState(final MoviesState state) {
        super.saveState(state);
        state.setSortType(sortType);
    }

    @Override
    public void refresh(@SortType final int sortType) {
        this.sortType = sortType;
        refresh();
    }

    @Override
    public int getSortType() {
        return this.sortType;
    }

    private void refresh() {
        view.showRefreshing();

        final Disposable disposable = moviesActionApi
                .action(sortType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::showMovies, this::onError);
        getCompositeDisposable().add(disposable);
    }

    private void onError(final Throwable th) {
        Timber.e(th.getMessage(), th);
    }
}

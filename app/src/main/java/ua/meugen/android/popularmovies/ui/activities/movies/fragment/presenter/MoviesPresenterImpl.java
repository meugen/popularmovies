package ua.meugen.android.popularmovies.ui.activities.movies.fragment.presenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ua.meugen.android.popularmovies.model.SortType;
import ua.meugen.android.popularmovies.model.api.ModelApi;
import ua.meugen.android.popularmovies.model.db.dao.MoviesDao;
import ua.meugen.android.popularmovies.model.db.entity.MovieItem;
import ua.meugen.android.popularmovies.model.db.execs.Executor;
import ua.meugen.android.popularmovies.model.db.execs.MoviesData;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.presenter.BaseMvpPresenter;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.state.MoviesState;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.view.MoviesView;

/**
 * @author meugen
 */

public class MoviesPresenterImpl extends BaseMvpPresenter<MoviesView, MoviesState>
        implements MoviesPresenter {

    @Inject ModelApi modelApi;
    @Inject MoviesDao moviesDao;
    @Inject Executor<MoviesData> mergeMoviesExecutor;

    @SortType
    private int sortType;

    @Inject
    public MoviesPresenterImpl() {}

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

        Observable<? extends MoviesData> observable;
        if (sortType == SortType.POPULAR) {
            observable = modelApi.getPopularMovies()
                    .map(dto -> dto.results)
                    .map(movies -> new MoviesData(movies, SortType.POPULAR, true))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onExceptionResumeNext(getMoviesByStatus(SortType.POPULAR));
        } else if (sortType == SortType.TOP_RATED) {
            observable = modelApi.getTopRatedMovies()
                    .map(dto -> dto.results)
                    .map(movies -> new MoviesData(movies, SortType.TOP_RATED, true))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onExceptionResumeNext(getMoviesByStatus(SortType.TOP_RATED));
        } else if (sortType == SortType.FAVORITES) {
            observable = getMoviesByStatus(SortType.FAVORITES)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            throw new IllegalArgumentException("Unknown sort type");
        }
        final Disposable disposable = observable
                .subscribe(this::onMovies);
        getCompositeDisposable().add(disposable);
    }

    private Observable<MoviesData> getMoviesByStatus(final int status) {
        final Single<List<MovieItem>> single = Single.create(emitter -> {
            List<MovieItem> movies = moviesDao.byStatus(status);
            if (!emitter.isDisposed()) {
                emitter.onSuccess(movies);
            }
        });
        return single.toObservable()
                .map(movies -> new MoviesData(movies, status, false));
    }

    private void onMovies(final MoviesData data) {
        if (data.isNeedToSave()) {
            final Disposable disposable = mergeMoviesExecutor
                    .executeTransactionAsync(data)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
            getCompositeDisposable().add(disposable);
        }
        view.showMovies(data.getMovies());
    }
}

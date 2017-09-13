package ua.meugen.android.popularmovies.ui.activities.movies.fragment.presenter;

import com.pushtorefresh.storio.operations.PreparedOperation;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ua.meugen.android.popularmovies.app.annotations.SortType;
import ua.meugen.android.popularmovies.app.api.ModelApi;
import ua.meugen.android.popularmovies.app.di.db.movie.MovieContract;
import ua.meugen.android.popularmovies.app.di.ints.TransactionExecutor;
import ua.meugen.android.popularmovies.app.executors.MoviesData;
import ua.meugen.android.popularmovies.app.utils.RxUtils;
import ua.meugen.android.popularmovies.model.responses.MovieItemDto;
import ua.meugen.android.popularmovies.model.responses.PagedMoviesDto;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.presenter.BaseMvpPresenter;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.state.MoviesState;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.view.MoviesView;

/**
 * @author meugen
 */

public class MoviesPresenterImpl extends BaseMvpPresenter<MoviesView, MoviesState>
        implements MoviesPresenter, MovieContract {

    private final ModelApi modelApi;
    private final StorIOSQLite storIOSQLite;
    private final TransactionExecutor<MoviesData> mergeMoviesExecutor;

    @SortType
    private int sortType;

    @Inject
    public MoviesPresenterImpl(
            final ModelApi modelApi,
            final StorIOSQLite storIOSQLite,
            final TransactionExecutor<MoviesData> mergeMoviesExecutor) {
        this.modelApi = modelApi;
        this.storIOSQLite = storIOSQLite;
        this.mergeMoviesExecutor = mergeMoviesExecutor;
    }

    @Override
    public void onCreate(final MoviesState state) {
        super.onCreate(state);
        refresh(state.getSortType());
    }

    @Override
    public void onSaveInstanceState(final MoviesState state) {
        super.onSaveInstanceState(state);
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
                    .map(PagedMoviesDto::getResults)
                    .map(movies -> new MoviesData(movies, SortType.POPULAR, true))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onExceptionResumeNext(getMoviesByStatus(SortType.POPULAR));
        } else if (sortType == SortType.TOP_RATED) {
            observable = modelApi.getTopRatedMovies()
                    .map(PagedMoviesDto::getResults)
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
        final Query query = Query.builder()
                .table(TABLE)
                .where("(" + FIELD_STATUS + "&" + status + ")=" + status)
                .build();
        final PreparedOperation<List<MovieItemDto>> operation =
                storIOSQLite.get()
                .listOfObjects(MovieItemDto.class)
                .withQuery(query)
                .prepare();
        return RxUtils.asObservable(operation)
                .map(movies -> new MoviesData(movies, status, false));
    }

    private void onMovies(final MoviesData data) {
        if (data.isNeedToSave()) {
            final Disposable disposable = mergeMoviesExecutor
                    .executeTransactionAsync(storIOSQLite, data)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
            getCompositeDisposable().add(disposable);
        }
        view.showMovies(data.getMovies());
    }
}

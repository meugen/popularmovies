package ua.meugen.android.popularmovies.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.AsyncSubject;
import rx.subscriptions.CompositeSubscription;
import ua.meugen.android.popularmovies.app.executors.MoviesData;
import ua.meugen.android.popularmovies.model.responses.MovieItemDto;
import ua.meugen.android.popularmovies.model.responses.PagedMoviesDto;
import ua.meugen.android.popularmovies.presenter.annotations.SortType;
import ua.meugen.android.popularmovies.presenter.api.ModelApi;
import ua.meugen.android.popularmovies.presenter.helpers.TransactionExecutor;
import ua.meugen.android.popularmovies.ui.MoviesView;

/**
 * @author meugen
 */

public class MoviesPresenter implements MvpPresenter<MoviesView> {

    private final ModelApi modelApi;
    private final Realm realm;
    private final TransactionExecutor<MoviesData> mergeMoviesExecutor;

    private MoviesView view;
    private CompositeSubscription compositeSubscription;

    @SortType
    private int sortType;

    @Inject
    public MoviesPresenter(
            final ModelApi modelApi,
            final Realm realm,
            @Named("merge-movies")
            final TransactionExecutor<MoviesData> mergeMoviesExecutor) {
        this.modelApi = modelApi;
        this.realm = realm;
        this.mergeMoviesExecutor = mergeMoviesExecutor;
    }

    @Override
    public void attachView(final MoviesView view) {
        this.view = view;
        init();
    }

    @Override
    public void detachView(final boolean retainInstance) {
        this.view = null;
        reset(false);
    }

    private void refresh() {
        view.showRefreshing();

        Observable<? extends MoviesData> observable;
        if (sortType == SortType.POPULAR) {
            observable = modelApi.getPopularMovies()
                    .map(PagedMoviesDto::getResults)
                    .map(movies -> new MoviesData(movies, MovieItemDto.POPULAR, true))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onExceptionResumeNext(getMoviesByStatus(MovieItemDto.POPULAR));
        } else if (sortType == SortType.TOP_RATED) {
            observable = modelApi.getTopRatedMovies()
                    .map(PagedMoviesDto::getResults)
                    .map(movies -> new MoviesData(movies, MovieItemDto.TOP_RATED, true))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onExceptionResumeNext(getMoviesByStatus(MovieItemDto.TOP_RATED));
        } else if (sortType == SortType.FAVORITES) {
            observable = getMoviesByStatus(MovieItemDto.FAVORITES)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            throw new IllegalArgumentException("Unknown sort type");
        }
        final Subscription subscription = observable
                .subscribe(this::onMovies);
        compositeSubscription.add(subscription);
    }

    private Observable<MoviesData> getMoviesByStatus(final String status) {
        final AsyncSubject<List<MovieItemDto>> subject = AsyncSubject.create();
        final RealmResults<MovieItemDto> results = realm
                .where(MovieItemDto.class)
                .equalTo(status, true)
                .findAllAsync();
        results.addChangeListener(newResults -> {
            subject.onNext(newResults);
            subject.onCompleted();
            results.removeAllChangeListeners();
        });
        return subject.asObservable()
                .map(movies -> new MoviesData(movies, status, false));
    }

    private void onMovies(final MoviesData data) {
        if (data.isNeedToSave()) {
            mergeMoviesExecutor.executeTransactionAsync(realm, data);
        }
        view.showMovies(data.getMovies());
    }

    private void init() {
        compositeSubscription = new CompositeSubscription();
    }

    private void reset(final boolean isInit) {
        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
            compositeSubscription = null;
        }
        if (isInit) {
            init();
        }
    }

    public void refresh(
            @SortType final int sortType) {
        reset(true);
        this.sortType = sortType;
        refresh();
    }

    public int getSortType() {
        return sortType;
    }
}

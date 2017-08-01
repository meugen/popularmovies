package ua.meugen.android.popularmovies.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import ua.meugen.android.popularmovies.model.api.ModelApi;
import ua.meugen.android.popularmovies.model.responses.MovieItemDto;
import ua.meugen.android.popularmovies.model.responses.PagedMoviesDto;
import ua.meugen.android.popularmovies.model.transactions.MergeMoviesTransaction;
import ua.meugen.android.popularmovies.presenter.annotations.SortType;
import ua.meugen.android.popularmovies.view.MoviesView;

/**
 * @author meugen
 */

public class MoviesPresenter implements MvpPresenter<MoviesView> {

    private final ModelApi modelApi;
    private final Realm realm;

    private MoviesView view;
    private CompositeSubscription compositeSubscription;

    @SortType
    private int sortType;

    @Inject
    public MoviesPresenter(
            final ModelApi modelApi,
            final Realm realm) {
        this.modelApi = modelApi;
        this.realm = realm;
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

        Observable<? extends List<MovieItemDto>> observable;
        if (sortType == SortType.POPULAR) {
            observable = modelApi.getPopularMovies()
                    .map(PagedMoviesDto::getResults)
                    .subscribeOn(Schedulers.io());
        } else if (sortType == SortType.TOP_RATED) {
            observable = modelApi.getTopRatedMovies()
                    .map(PagedMoviesDto::getResults)
                    .subscribeOn(Schedulers.io());
        } else if (sortType == SortType.FAVORITES) {
            observable = realm.where(MovieItemDto.class)
                    .equalTo("favorite", true)
                    .findAllAsync().asObservable();
        } else {
            throw new IllegalArgumentException("Unknown sort type");
        }
        final Subscription subscription = observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onMovies);
        compositeSubscription.add(subscription);
    }

    private void onMovies(final List<MovieItemDto> movies) {
        if (sortType != SortType.FAVORITES) {
            realm.executeTransactionAsync(
                    new MergeMoviesTransaction(movies));
        }
        view.showMovies(movies);
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

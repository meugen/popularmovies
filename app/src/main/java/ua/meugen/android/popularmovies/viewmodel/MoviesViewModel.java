package ua.meugen.android.popularmovies.viewmodel;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import ua.meugen.android.popularmovies.PopularMovies;
import ua.meugen.android.popularmovies.model.api.ModelApi;
import ua.meugen.android.popularmovies.model.dto.MovieItemDto;
import ua.meugen.android.popularmovies.model.dto.PagedMoviesDto;
import ua.meugen.android.popularmovies.model.transactions.MergeMoviesTransaction;
import ua.meugen.android.popularmovies.view.activities.MovieDetailsActivity;
import ua.meugen.android.popularmovies.view.adapters.MoviesAdapter;
import ua.meugen.android.popularmovies.viewmodel.listeners.OnMovieClickListener;

/**
 * @author meugen
 */

public class MoviesViewModel implements SwipeRefreshLayout.OnRefreshListener,
        OnMovieClickListener {

    private final ModelApi modelApi;
    private final Realm realm;

    private final Context context;
    private CompositeSubscription compositeSubscription;

    public final ObservableBoolean refreshing;
    public final MoviesAdapter adapter;

    @PopularMovies.SortType
    private int sortType;

    @Inject
    public MoviesViewModel(
            final Context context,
            final ModelApi modelApi,
            final Realm realm) {
        this.context = context;
        this.modelApi = modelApi;
        this.realm = realm;

        this.compositeSubscription = new CompositeSubscription();
        refreshing = new ObservableBoolean(false);
        adapter = new MoviesAdapter(context, this);
        sortType = PopularMovies.from(context).getSortType();
    }

    public void refresh() {
        refreshing.set(true);

        Observable<? extends List<MovieItemDto>> observable;
        if (sortType == PopularMovies.SORT_TYPE_POPULAR) {
            observable = modelApi.popularMovies()
                    .map(PagedMoviesDto::getResults)
                    .subscribeOn(Schedulers.io());
        } else if (sortType == PopularMovies.SORT_TYPE_TOP_RATED) {
            observable = modelApi.topRatedMovies()
                    .map(PagedMoviesDto::getResults)
                    .subscribeOn(Schedulers.io());
        } else if (sortType == PopularMovies.SORT_TYPE_FAVORITES) {
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
        if (sortType != PopularMovies.SORT_TYPE_FAVORITES) {
            realm.executeTransactionAsync(
                    new MergeMoviesTransaction(movies));
        }
        adapter.setMovies(movies);
        refreshing.set(false);
    }

    private void refreshWithSortType(
            @PopularMovies.SortType final int sortType) {
        reset();
        this.sortType = sortType;
        PopularMovies.from(context).setSortType(sortType);
        refresh();
    }

    public void reset() {
        if (!compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
        }
        compositeSubscription = new CompositeSubscription();
    }

    public void refreshPopular() {
        refreshWithSortType(PopularMovies.SORT_TYPE_POPULAR);
    }

    public void refreshTopRated() {
        refreshWithSortType(PopularMovies.SORT_TYPE_TOP_RATED);
    }

    public void refreshFavorites() {
        refreshWithSortType(PopularMovies.SORT_TYPE_FAVORITES);
    }

    public int getSortType() {
        return sortType;
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    @Override
    public void onMovieClick(final int movieId) {
        MovieDetailsActivity.start(context, movieId);
    }
}

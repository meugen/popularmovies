package ua.meugen.android.popularmovies.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import java.util.Observable;
import java.util.UUID;

import javax.inject.Inject;

import io.realm.Realm;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import ua.meugen.android.popularmovies.PopularMovies;
import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.model.Session;
import ua.meugen.android.popularmovies.model.api.ModelApi;
import ua.meugen.android.popularmovies.model.responses.BaseDto;
import ua.meugen.android.popularmovies.model.responses.MovieItemDto;
import ua.meugen.android.popularmovies.model.responses.NewGuestSessionDto;
import ua.meugen.android.popularmovies.presenter.helpers.SessionStorage;
import ua.meugen.android.popularmovies.view.MovieDetailsView;
import ua.meugen.android.popularmovies.view.dialogs.RateMovieDialog;
import ua.meugen.android.popularmovies.view.dialogs.SelectSessionTypeDialog;

/**
 * @author meugen
 */

public class MovieDetailsPresenter implements
        MvpPresenter<MovieDetailsView> {

    private static final String TAG = MovieDetailsPresenter.class.getSimpleName();

    private final ModelApi modelApi;
    private final Realm realm;
    private final SessionStorage sessionStorage;

    private MovieDetailsView view;
    private CompositeSubscription compositeSubscription;

    private MovieItemDto movie;
    private int movieId;

    @Inject
    public MovieDetailsPresenter(
            final ModelApi modelApi,
            final Realm realm,
            final SessionStorage sessionStorage) {
        this.modelApi = modelApi;
        this.realm = realm;
        this.sessionStorage = sessionStorage;

        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void attachView(final MovieDetailsView view) {
        this.view = view;
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView(final boolean retainInstance) {
        this.view = null;
        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
            compositeSubscription = null;
        }
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(final int movieId) {
        this.movieId = movieId;
    }

    public void load() {
        Subscription subscription = realm
                .where(MovieItemDto.class)
                .equalTo("id", movieId)
                .findFirstAsync()
                .<MovieItemDto>asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::gotMovie);
        compositeSubscription.add(subscription);
    }

    private void gotMovie(final MovieItemDto movie) {
        this.movie = movie;
        view.gotMovie(movie);
    }

    public void rateMovie() {
        final Session session = sessionStorage.getSession();
        if (session == null) {
            view.selectSession();
        } else {
            view.rateMovieWithSession();
        }
    }

    public void switchFavorites() {
        if (movie != null) {
            realm.beginTransaction();
            movie.setFavorite(!movie.isFavorite());
            realm.commitTransaction();
        }
    }

    public void onMovieRated(final float value) {
        final Session session = sessionStorage.getSession();
        final Subscription subscription = modelApi
                .rateMovie(session, movieId, value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::rateMovieSuccess, this::rateMovieError);
        compositeSubscription.add(subscription);
    }

    private void rateMovieSuccess(final BaseDto dto) {
        if (dto.isSuccess()) {
            view.sendMessage(context.getText(R.string.rate_movie_result_ok));
        } else {
            view.sendMessage(context.getString(
                    R.string.rate_movie_result_server_error,
                    dto.getStatusMessage()));
        }
    }

    private void rateMovieError(final Throwable th) {
        Log.e(TAG, th.getMessage(), th);
        view.sendMessage(context.getText(R.string.rate_movie_result_network_error));
    }

    public void createGuestSession() {
        Subscription subscription = modelApi
                .createNewGuestSession()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGuestSessionSuccess, this::onGuestSessionError);
        compositeSubscription.add(subscription);
    }

    private void onGuestSessionSuccess(final NewGuestSessionDto dto) {
        if (dto.isSuccess()) {
            sessionStorage.storeSession(
                    dto.getGuestSessionId(), true,
                    dto.getExpiresAt());
            view.rateMovieWithSession();
        } else {
            view.sendMessage(context.getString(
                    R.string.guest_session_result_server_error,
                    dto.getStatusMessage()));
        }
    }

    private void onGuestSessionError(final Throwable th) {
        Log.e(TAG, th.getMessage(), th);
        view.sendMessage(context.getText(R.string.guest_session_result_network_error));
    }
}

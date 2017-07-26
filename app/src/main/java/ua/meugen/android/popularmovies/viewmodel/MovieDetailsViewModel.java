package ua.meugen.android.popularmovies.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;

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
import ua.meugen.android.popularmovies.view.utils.BundleUtils;
import ua.meugen.android.popularmovies.view.ListenersCollector;
import ua.meugen.android.popularmovies.view.dialogs.RateMovieDialog;
import ua.meugen.android.popularmovies.view.dialogs.SelectSessionTypeDialog;

/**
 * @author meugen
 */

public class MovieDetailsViewModel extends Observable
        implements RateMovieDialog.OnMovieRatedListener,
        SelectSessionTypeDialog.OnSessionTypeSelectedListener {

    private static final String TAG = MovieDetailsViewModel.class.getSimpleName();

    public static final Integer ACTION_SELECT_SESSION = 1;
    public static final Integer ACTION_RATE_MOVIE = 2;
    public static final Integer ACTION_AUTH_USER_SESSION = 3;

    private static final String PARAM_MOVIE_ID = "movieId";
    private static final String PARAM_LISTENER_UUID
            = "listenerUUID";

    public static void bindMovieId(final Bundle args, final int movieId) {
        args.putInt(PARAM_MOVIE_ID, movieId);
    }

    private final Context context;
    private final ModelApi modelApi;
    private final Realm realm;

    public final ObservableField<MovieItemDto> movie;
    private final CompositeSubscription compositeSubscription;

    private int movieId;
    private UUID listenerUUID;

    @Inject
    public MovieDetailsViewModel(
            final Context context,
            final ModelApi modelApi,
            final Realm realm) {
        this.context = context;
        this.modelApi = modelApi;
        this.realm = realm;

        movie = new ObservableField<>();
        compositeSubscription = new CompositeSubscription();
    }

    public void restoreInstanceState(final Bundle state) {
        movieId = state.getInt(PARAM_MOVIE_ID);
        listenerUUID = BundleUtils.getUUID(state,
                PARAM_LISTENER_UUID);
    }

    public void saveInstanceState(final Bundle outState) {
        outState.putInt(PARAM_MOVIE_ID, movieId);
        BundleUtils.putUUID(outState, PARAM_LISTENER_UUID, listenerUUID);
    }

    public void registerListeners(final ListenersCollector collector) {
        listenerUUID = collector.registerListener(listenerUUID, this);
    }

    public void load() {
        Subscription subscription = realm
                .where(MovieItemDto.class)
                .equalTo("id", movieId)
                .findFirstAsync()
                .<MovieItemDto>asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movie::set);
        compositeSubscription.add(subscription);
    }

    public void reset(final ListenersCollector collector) {
        collector.unregisterListener(listenerUUID);
        if (!compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
        }
    }

    public void rateMovie() {
        final Session session = PopularMovies.from(context).getSession();
        if (session == null) {
            setChanged();
            notifyObservers(ACTION_SELECT_SESSION);
        } else {
            rateMovieWithSession();
        }
    }

    public void rateMovieWithSession() {
        setChanged();
        notifyObservers(ACTION_RATE_MOVIE);
    }

    public void switchFavorites() {
        MovieItemDto persistMovie = movie.get();
        if (persistMovie != null) {
            realm.beginTransaction();
            persistMovie.setFavorite(!persistMovie.isFavorite());
            realm.commitTransaction();
        }
    }

    public void selectSessionType(final FragmentManager fm) {
        final SelectSessionTypeDialog dialog = SelectSessionTypeDialog
                .newInstance(listenerUUID);
        dialog.show(fm, "select_session_type");
    }

    public void rateMovieWithSession(final FragmentManager fm) {
        final RateMovieDialog dialog = RateMovieDialog
                .newInstance(listenerUUID);
        dialog.show(fm, "rate_movie");
    }

    @Override
    public void onMovieRated(final float value) {
        final Session session = PopularMovies.from(context).getSession();
        final Subscription subscription = modelApi
                .rateMovie(session, movieId, value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::rateMovieSuccess, this::rateMovieError);
        compositeSubscription.add(subscription);
    }

    private void rateMovieSuccess(final BaseDto dto) {
        if (dto.isSuccess()) {
            sendMessage(context.getText(R.string.rate_movie_result_ok));
        } else {
            sendMessage(context.getString(
                    R.string.rate_movie_result_server_error,
                    dto.getStatusMessage()));
        }
    }

    private void rateMovieError(final Throwable th) {
        Log.e(TAG, th.getMessage(), th);
        sendMessage(context.getText(R.string.rate_movie_result_network_error));
    }

    private void sendMessage(final CharSequence text) {
        setChanged();
        notifyObservers(text);
    }

    @Override
    public void onUserSessionSelected() {
        setChanged();
        notifyObservers(ACTION_AUTH_USER_SESSION);
    }

    @Override
    public void onGuestSessionSelected() {
        Subscription subscription = modelApi
                .createNewGuestSession()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGuestSessionSuccess, this::onGuestSessionError);
        compositeSubscription.add(subscription);
    }

    private void onGuestSessionSuccess(final NewGuestSessionDto dto) {
        if (dto.isSuccess()) {
            PopularMovies.from(context).storeSession(
                    dto.getGuestSessionId(), true,
                    dto.getExpiresAt());
            rateMovieWithSession();
        } else {
            sendMessage(context.getString(
                    R.string.guest_session_result_server_error,
                    dto.getStatusMessage()));
        }
    }

    private void onGuestSessionError(final Throwable th) {
        Log.e(TAG, th.getMessage(), th);
        sendMessage(context.getText(R.string.guest_session_result_network_error));
    }
}

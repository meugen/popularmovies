package ua.meugen.android.popularmovies.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import java.util.Date;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.AsyncSubject;
import io.realm.ObjectChangeSet;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import io.realm.RealmObjectChangeListener;
import timber.log.Timber;
import ua.meugen.android.popularmovies.model.Session;
import ua.meugen.android.popularmovies.model.responses.BaseDto;
import ua.meugen.android.popularmovies.model.responses.MovieItemDto;
import ua.meugen.android.popularmovies.model.responses.NewGuestSessionDto;
import ua.meugen.android.popularmovies.presenter.api.ModelApi;
import ua.meugen.android.popularmovies.presenter.helpers.SessionStorage;
import ua.meugen.android.popularmovies.ui.MovieDetailsView;

/**
 * @author meugen
 */

public class MovieDetailsPresenter implements
        MvpPresenter<MovieDetailsView> {

    private final ModelApi modelApi;
    private final Realm realm;
    private final SessionStorage sessionStorage;

    private MovieDetailsView view;
    private CompositeDisposable compositeDisposable;

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

        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(final MovieDetailsView view) {
        this.view = view;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void detachView(final boolean retainInstance) {
        this.view = null;
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
            compositeDisposable = null;
        }
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(final int movieId) {
        this.movieId = movieId;
    }

    public void load() {
        MovieItemDto result = realm
                .where(MovieItemDto.class)
                .equalTo("id", movieId)
                .findFirstAsync();
        AsyncSubject<MovieItemDto> subject = AsyncSubject.create();
        result.addChangeListener(newResult -> {
            subject.onNext((MovieItemDto) newResult);
            subject.onComplete();
            result.removeAllChangeListeners();
        });
        Disposable disposable = subject
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::gotMovie);
        compositeDisposable.add(disposable);
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

    public void storeUserSession(final String session) {
        sessionStorage.storeSession(session, false,
                new Date(Long.MAX_VALUE));
    }

    public void switchFavorites() {
        if (movie != null) {
            realm.beginTransaction();
            movie.setFavorites(!movie.isFavorites());
            realm.commitTransaction();
        }
    }

    public void onMovieRated(final float value) {
        final Session session = sessionStorage.getSession();
        final Disposable disposable = modelApi
                .rateMovie(session, movieId, value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::rateMovieSuccess, this::rateMovieError);
        compositeDisposable.add(disposable);
    }

    private void rateMovieSuccess(final BaseDto dto) {
        if (dto.isSuccess()) {
            view.onMovieRatedSuccess();
        } else {
            view.onServerError(dto.getStatusMessage());
        }
    }

    private void rateMovieError(final Throwable th) {
        Timber.e(th.getMessage(), th);
        view.onError();
    }

    public void createGuestSession() {
        Disposable disposable = modelApi
                .createNewGuestSession()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGuestSessionSuccess, this::onGuestSessionError);
        compositeDisposable.add(disposable);
    }

    private void onGuestSessionSuccess(final NewGuestSessionDto dto) {
        if (dto.isSuccess()) {
            sessionStorage.storeSession(
                    dto.getGuestSessionId(), true,
                    dto.getExpiresAt());
            view.rateMovieWithSession();
        } else {
            view.onServerError(dto.getStatusMessage());
        }
    }

    private void onGuestSessionError(final Throwable th) {
        Timber.e(th.getMessage(), th);
        view.onError();
    }
}

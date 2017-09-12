package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details.presenter;

import com.pushtorefresh.storio.operations.PreparedOperation;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import ua.meugen.android.popularmovies.app.annotations.SortType;
import ua.meugen.android.popularmovies.app.api.ModelApi;
import ua.meugen.android.popularmovies.app.di.db.movie.MovieContract;
import ua.meugen.android.popularmovies.app.di.ints.SessionStorage;
import ua.meugen.android.popularmovies.app.utils.RxUtils;
import ua.meugen.android.popularmovies.model.Session;
import ua.meugen.android.popularmovies.model.responses.BaseDto;
import ua.meugen.android.popularmovies.model.responses.MovieItemDto;
import ua.meugen.android.popularmovies.model.responses.NewGuestSessionDto;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.presenter.BaseMvpPresenter;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details.state.MovieDetailsState;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details.view.MovieDetailsView;

/**
 * @author meugen
 */

public class MovieDetailsPresenterImpl extends BaseMvpPresenter<MovieDetailsView, MovieDetailsState>
        implements MovieDetailsPresenter {

    private final ModelApi modelApi;
    private final StorIOSQLite storIOSQLite;
    private final SessionStorage sessionStorage;

    private MovieItemDto movie;
    private int movieId;
    private UUID listenerUUID;

    @Inject
    public MovieDetailsPresenterImpl(
            final ModelApi modelApi,
            final StorIOSQLite storIOSQLite,
            final SessionStorage sessionStorage) {
        this.modelApi = modelApi;
        this.storIOSQLite = storIOSQLite;
        this.sessionStorage = sessionStorage;
    }

    @Override
    public void onCreate(final MovieDetailsState state) {
        super.onCreate(state);
        movieId = state.getMovieId();
        listenerUUID = state.getListenerUUID();
    }

    @Override
    public void onSaveInstanceState(final MovieDetailsState state) {
        super.onSaveInstanceState(state);
        state.setMovieId(movieId);
        state.setListenerUUID(listenerUUID);
    }

    @Override
    public void onStart() {
        super.onStart();
        load();
    }

    private void load() {
        Query query = Query.builder()
                .table(MovieContract.TABLE)
                .where(MovieContract.FIELD_ID + "=?")
                .whereArgs(movieId)
                .limit(1)
                .build();
        PreparedOperation<MovieItemDto> operation = storIOSQLite
                .get().object(MovieItemDto.class)
                .withQuery(query)
                .prepare();
        Disposable disposable = RxUtils.asSingle(operation)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::gotMovie);
        getCompositeDisposable().add(disposable);
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
            if ((movie.getStatus() & SortType.FAVORITES) == SortType.FAVORITES) {
                movie.setStatus(movie.getStatus() & ~SortType.FAVORITES);
            } else {
                movie.setStatus(movie.getStatus() | SortType.FAVORITES);
            }
            final PreparedOperation<PutResult> operation = storIOSQLite
                    .put().object(movie).prepare();
            Disposable disposable = RxUtils.asCompletable(operation)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
            getCompositeDisposable().add(disposable);
        }
    }

    public void onMovieRated(final float value) {
        final Session session = sessionStorage.getSession();
        final Disposable disposable = modelApi
                .rateMovie(session, movieId, value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::rateMovieSuccess, this::rateMovieError);
        getCompositeDisposable().add(disposable);
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
        getCompositeDisposable().add(disposable);
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

    @Override
    public UUID getListenerUUID() {
        return listenerUUID;
    }

    @Override
    public void setListenerUUID(final UUID uuid) {
        listenerUUID = uuid;
    }
}

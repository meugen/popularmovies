package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details.presenter;

import org.javatuples.Pair;

import java.util.Collections;
import java.util.Date;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import ua.meugen.android.popularmovies.model.SortType;
import ua.meugen.android.popularmovies.model.api.AppActionApi;
import ua.meugen.android.popularmovies.model.db.dao.MoviesDao;
import ua.meugen.android.popularmovies.model.db.entity.MovieItem;
import ua.meugen.android.popularmovies.model.network.resp.BaseResponse;
import ua.meugen.android.popularmovies.model.network.resp.NewGuestSessionResponse;
import ua.meugen.android.popularmovies.model.session.Session;
import ua.meugen.android.popularmovies.model.session.SessionStorage;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.presenter.BaseMvpPresenter;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details.state.MovieDetailsState;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details.view.MovieDetailsView;
import ua.meugen.android.popularmovies.ui.rxloader.LifecycleHandler;

/**
 * @author meugen
 */

public class MovieDetailsPresenterImpl extends BaseMvpPresenter<MovieDetailsView, MovieDetailsState>
        implements MovieDetailsPresenter {

    @Inject AppActionApi<Integer, MovieItem> movieByIdActionApi;
    @Inject SessionStorage sessionStorage;
    @Inject AppActionApi<Pair<Integer, Float>, BaseResponse> rateMovieActionApi;
    @Inject AppActionApi<Void, NewGuestSessionResponse> newGuestSessionActionApi;
    @Inject LifecycleHandler lifecycleHandler;
    @Inject AppActionApi<MovieItem, Void> switchFavoriteActionApi;

    private MovieItem movie;
    private int movieId;

    @Inject
    MovieDetailsPresenterImpl() {}

    @Override
    public void restoreState(final MovieDetailsState state) {
        super.restoreState(state);
        movieId = state.getMovieId();
    }

    @Override
    public void saveState(final MovieDetailsState state) {
        super.saveState(state);
        state.setMovieId(movieId);
    }

    public void load() {
        Disposable disposable = movieByIdActionApi
                .action(movieId)
                .compose(lifecycleHandler.load(MOVIE_LOADER_ID))
                .subscribe(this::gotMovie);
        getCompositeDisposable().add(disposable);
    }

    @Override
    public void resume() {
        if (lifecycleHandler.hasLoader(RATE_MOVIE_LOADER_ID)) {
            final Disposable rateMovie = lifecycleHandler
                    .<BaseResponse>next(RATE_MOVIE_LOADER_ID)
                    .subscribe(this::onRateMovieSuccess, this::onRateMovieError);
            getCompositeDisposable().add(rateMovie);
        }
        if (lifecycleHandler.hasLoader(GUEST_SESSION_LOADER_ID)) {
            final Disposable guestSession = lifecycleHandler
                    .<NewGuestSessionResponse>next(GUEST_SESSION_LOADER_ID)
                    .subscribe(this::onGuestSessionSuccess, this::onGuestSessionError);
            getCompositeDisposable().add(guestSession);
        }
    }

    private void gotMovie(final MovieItem movie) {
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
            Disposable disposable = switchFavoriteActionApi.action(movie)
                    .compose(lifecycleHandler.reload(MERGE_MOVIE_LOADER_ID))
                    .subscribe();
            getCompositeDisposable().add(disposable);
        }
    }

    public void onMovieRated(final float value) {
        final Disposable disposable = rateMovieActionApi
                .action(Pair.with(movieId, value))
                .compose(lifecycleHandler.reload(RATE_MOVIE_LOADER_ID))
                .subscribe(this::onRateMovieSuccess, this::onRateMovieError);
        getCompositeDisposable().add(disposable);
    }

    private void onRateMovieSuccess(final BaseResponse response) {
        lifecycleHandler.clear(RATE_MOVIE_LOADER_ID);
        if (response.isSuccess()) {
            view.onMovieRatedSuccess();
        } else {
            view.onServerError(response.getStatusMessage());
        }
    }

    private void onRateMovieError(final Throwable th) {
        lifecycleHandler.clear(RATE_MOVIE_LOADER_ID);
        Timber.e(th.getMessage(), th);
        view.onError();
    }

    public void createGuestSession() {
        Disposable disposable = newGuestSessionActionApi
                .action(null)
                .compose(lifecycleHandler.reload(GUEST_SESSION_LOADER_ID))
                .subscribe(this::onGuestSessionSuccess, this::onGuestSessionError);
        getCompositeDisposable().add(disposable);
    }

    private void onGuestSessionSuccess(final NewGuestSessionResponse response) {
        lifecycleHandler.clear(GUEST_SESSION_LOADER_ID);
        if (response.isSuccess()) {
            sessionStorage.storeSession(
                    response.getGuestSessionId(), true,
                    response.getExpiresAt());
            view.rateMovieWithSession();
        } else {
            view.onServerError(response.getStatusMessage());
        }
    }

    private void onGuestSessionError(final Throwable th) {
        lifecycleHandler.clear(GUEST_SESSION_LOADER_ID);
        Timber.e(th.getMessage(), th);
        view.onError();
    }
}

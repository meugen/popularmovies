package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details.presenter;

import org.javatuples.Pair;

import java.util.Collections;
import java.util.Date;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.Single;
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

/**
 * @author meugen
 */

public class MovieDetailsPresenterImpl extends BaseMvpPresenter<MovieDetailsView, MovieDetailsState>
        implements MovieDetailsPresenter {

    @Inject AppActionApi<Integer, MovieItem> movieByIdActionApi;
    @Inject SessionStorage sessionStorage;
    @Inject MoviesDao moviesDao;
    @Inject AppActionApi<Pair<Integer, Float>, BaseResponse> rateMovieActionApi;
    @Inject AppActionApi<Void, NewGuestSessionResponse> newGuestSessionActionApi;

    private MovieItem movie;
    private int movieId;

    @Inject
    public MovieDetailsPresenterImpl() {}

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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::gotMovie);
        getCompositeDisposable().add(disposable);
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
            if ((movie.status & SortType.FAVORITES) == SortType.FAVORITES) {
                movie.status = movie.status & ~SortType.FAVORITES;
            } else {
                movie.status = movie.status | SortType.FAVORITES;
            }
            Disposable disposable = Completable
                    .create(this::mergeMovie)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
            getCompositeDisposable().add(disposable);
        }
    }

    private void mergeMovie(final CompletableEmitter emitter) {
        moviesDao.merge(Collections.singleton(movie));
        if (!emitter.isDisposed()) {
            emitter.onComplete();
        }
    }

    public void onMovieRated(final float value) {
        final Disposable disposable = rateMovieActionApi
                .action(Pair.with(movieId, value))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::rateMovieSuccess, this::rateMovieError);
        getCompositeDisposable().add(disposable);
    }

    private void rateMovieSuccess(final BaseResponse response) {
        if (response.success) {
            view.onMovieRatedSuccess();
        } else {
            view.onServerError(response.statusMessage);
        }
    }

    private void rateMovieError(final Throwable th) {
        Timber.e(th.getMessage(), th);
        view.onError();
    }

    public void createGuestSession() {
        Disposable disposable = newGuestSessionActionApi
                .action(null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGuestSessionSuccess, this::onGuestSessionError);
        getCompositeDisposable().add(disposable);
    }

    private void onGuestSessionSuccess(final NewGuestSessionResponse dto) {
        if (dto.success) {
            sessionStorage.storeSession(
                    dto.guestSessionId, true,
                    dto.expiresAt);
            view.rateMovieWithSession();
        } else {
            view.onServerError(dto.statusMessage);
        }
    }

    private void onGuestSessionError(final Throwable th) {
        Timber.e(th.getMessage(), th);
        view.onError();
    }
}

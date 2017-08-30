package ua.meugen.android.popularmovies.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import ua.meugen.android.popularmovies.model.responses.VideosDto;
import ua.meugen.android.popularmovies.presenter.api.ModelApi;
import ua.meugen.android.popularmovies.ui.MovieVideosView;

public class MovieVideosPresenter implements MvpPresenter<MovieVideosView> {

    private final ModelApi modelApi;

    private MovieVideosView view;
    private CompositeDisposable compositeDisposable;

    private int movieId;

    @Inject
    public MovieVideosPresenter(
            final ModelApi modelApi) {
        this.modelApi = modelApi;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(final int movieId) {
        this.movieId = movieId;
    }

    @Override
    public void attachView(final MovieVideosView view) {
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

    public void load() {
        Disposable disposable = modelApi.getMovieVideos(movieId)
                .map(VideosDto::getResults)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::onVideosLoaded, this::onVideosError);
        compositeDisposable.add(disposable);
    }

    private void onVideosError(final Throwable th) {
        Timber.e(th.getMessage(), th);
    }
}

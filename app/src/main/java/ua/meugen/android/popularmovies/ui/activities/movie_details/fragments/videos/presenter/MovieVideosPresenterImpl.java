package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.videos.presenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import ua.meugen.android.popularmovies.model.responses.VideosDto;
import ua.meugen.android.popularmovies.app.api.ModelApi;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.presenter.BaseMvpPresenter;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.videos.state.MovieVideosState;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.videos.view.MovieVideosView;

public class MovieVideosPresenterImpl extends BaseMvpPresenter<MovieVideosView, MovieVideosState>
        implements MovieVideosPresenter {

    private final ModelApi modelApi;

    private MovieVideosView view;
    private CompositeDisposable compositeDisposable;

    private int movieId;

    @Inject
    public MovieVideosPresenterImpl(
            final ModelApi modelApi) {
        this.modelApi = modelApi;
    }

    @Override
    public void onCreate(final MovieVideosState state) {
        super.onCreate(state);
        movieId = state.getMovieId();
    }

    @Override
    public void onSaveInstanceState(final MovieVideosState state) {
        super.onSaveInstanceState(state);
        state.setMovieId(movieId);
    }

    @Override
    public void onStart() {
        super.onStart();
        load();
    }

    private void load() {
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

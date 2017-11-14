package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.videos.presenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import ua.meugen.android.popularmovies.model.api.ModelApi;
import ua.meugen.android.popularmovies.model.db.dao.VideosDao;
import ua.meugen.android.popularmovies.model.db.entity.VideoItem;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.presenter.BaseMvpPresenter;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.videos.state.MovieVideosState;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.videos.view.MovieVideosView;

public class MovieVideosPresenterImpl extends BaseMvpPresenter<MovieVideosView, MovieVideosState>
        implements MovieVideosPresenter {

    @Inject ModelApi modelApi;
    @Inject VideosDao videosDao;

    private int movieId;

    @Inject
    MovieVideosPresenterImpl() {}

    @Override
    public void restoreState(final MovieVideosState state) {
        super.restoreState(state);
        movieId = state.getMovieId();
    }

    @Override
    public void saveState(final MovieVideosState state) {
        super.saveState(state);
        state.setMovieId(movieId);
    }

    public void load() {
        Disposable disposable = modelApi
                .getMovieVideos(movieId)
                .map(dto -> dto.results)
                .flatMap(this::storeToDb)
                .onErrorResumeNext(loadFromDb())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::onVideosLoaded, this::onVideosError);
        getCompositeDisposable().add(disposable);
    }

    private void onVideosError(final Throwable th) {
        Timber.e(th.getMessage(), th);
    }

    private Observable<List<VideoItem>> storeToDb(final List<VideoItem> videos) {
        videosDao.merge(videos);
        return Observable.just(videos);
    }

    private Observable<List<VideoItem>> loadFromDb() {
        return Observable.fromCallable(() -> videosDao.byMovieId(movieId));
    }
}

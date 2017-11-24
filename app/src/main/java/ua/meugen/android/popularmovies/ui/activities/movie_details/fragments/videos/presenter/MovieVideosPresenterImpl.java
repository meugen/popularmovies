package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.videos.presenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import ua.meugen.android.popularmovies.model.api.AppActionApi;
import ua.meugen.android.popularmovies.model.api.AppCachedActionApi;
import ua.meugen.android.popularmovies.model.db.dao.VideosDao;
import ua.meugen.android.popularmovies.model.db.entity.VideoItem;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.presenter.BaseMvpPresenter;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.videos.state.MovieVideosState;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.videos.view.MovieVideosView;
import ua.meugen.android.popularmovies.ui.rxloader.LifecycleHandler;
import ua.meugen.android.popularmovies.ui.utils.RxUtils;

public class MovieVideosPresenterImpl extends BaseMvpPresenter<MovieVideosView, MovieVideosState>
        implements MovieVideosPresenter {

    private static final int LOADER_ID = 1;

    @Inject AppCachedActionApi<Integer, List<VideoItem>> videosActionApi;
    @Inject LifecycleHandler lifecycleHandler;

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
        Disposable disposable = videosActionApi
                .action(movieId)
                .compose(RxUtils.async())
                .compose(lifecycleHandler.load(LOADER_ID))
                .subscribe(view::onVideosLoaded, this::onVideosError);
        getCompositeDisposable().add(disposable);
    }

    private void onVideosError(final Throwable th) {
        Timber.e(th.getMessage(), th);
    }
}

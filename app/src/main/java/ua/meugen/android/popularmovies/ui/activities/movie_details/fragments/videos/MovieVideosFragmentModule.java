package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.videos;

import android.support.v4.app.Fragment;

import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import ua.meugen.android.popularmovies.app.di.PerFragment;
import ua.meugen.android.popularmovies.model.api.AppCachedActionApi;
import ua.meugen.android.popularmovies.model.api.actions.MovieVideosActionApi;
import ua.meugen.android.popularmovies.model.cache.KeyGenerator;
import ua.meugen.android.popularmovies.model.db.entity.VideoItem;
import ua.meugen.android.popularmovies.model.db.execs.Executor;
import ua.meugen.android.popularmovies.model.db.execs.MergeVideosExecutor;
import ua.meugen.android.popularmovies.model.db.execs.data.VideosData;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.BaseFragmentModule;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.videos.adapters.OnClickVideoListener;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.videos.presenter.MovieVideosPresenter;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.videos.presenter.MovieVideosPresenterImpl;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.videos.state.MovieVideosState;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.videos.state.MovieVideosStateImpl;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.videos.view.MovieVideosView;

/**
 * @author meugen
 */
@Module(includes = BaseFragmentModule.class)
public abstract class MovieVideosFragmentModule {

    @Binds @PerFragment
    abstract MovieVideosPresenter bindPresenter(final MovieVideosPresenterImpl impl);

    @Binds @PerFragment
    abstract MovieVideosState bindState(final MovieVideosStateImpl impl);

    @Binds @PerFragment
    abstract MovieVideosView bindView(final MovieVideosFragment fragment);

    @Binds @PerFragment
    abstract Fragment bindFragment(final MovieVideosFragment fragment);

    @Binds @PerFragment
    abstract OnClickVideoListener bindClickVideoListener(final MovieVideosFragment fragment);

    @Binds @PerFragment
    abstract AppCachedActionApi<Integer, List<VideoItem>> bindVideosActionApi(
            final MovieVideosActionApi api);

    @Binds @PerFragment
    abstract Executor<VideosData> bindMergeVideosExecutor(final MergeVideosExecutor executor);

    @Provides @PerFragment
    static KeyGenerator<Integer> provideMovieVideosKeyGenerator() {
        return KeyGenerator.forMovieVideos();
    }
}

package ua.meugen.android.popularmovies.model.api.actions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import ua.meugen.android.popularmovies.model.api.ServerApi;
import ua.meugen.android.popularmovies.model.db.dao.VideosDao;
import ua.meugen.android.popularmovies.model.db.entity.VideoItem;
import ua.meugen.android.popularmovies.model.db.execs.Executor;
import ua.meugen.android.popularmovies.model.db.execs.data.VideosData;

/**
 * Created by meugen on 15.11.2017.
 */

public class MovieVideosActionApi extends OfflineFirstActionApi<Integer, List<VideoItem>> {

    @Inject ServerApi serverApi;
    @Inject VideosDao videosDao;
    @Inject Executor<VideosData> mergeVideosExecutor;

    @Inject
    MovieVideosActionApi() {}

    @NonNull
    @Override
    Single<List<VideoItem>> offlineData(final Integer movieId) {
        return Single.fromCallable(() -> videosDao.byMovieId(movieId));
    }

    @Nullable
    @Override
    Single<List<VideoItem>> networkData(final Integer movieId) {
        return serverApi.getMovieVideos(movieId).map(resp -> resp.results);
    }

    @Override
    void storeOffline(final Integer movieId, final List<VideoItem> videos) {
        final Disposable disposable = mergeVideosExecutor
                .executeTransaction(new VideosData(videos, movieId))
                .subscribe();
        getCompositeDisposable().add(disposable);
    }
}

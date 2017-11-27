package ua.meugen.android.popularmovies.model.api.actions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import ua.meugen.android.popularmovies.model.api.ServerApi;
import ua.meugen.android.popularmovies.model.cache.KeyGenerator;
import ua.meugen.android.popularmovies.model.db.dao.VideosDao;
import ua.meugen.android.popularmovies.model.db.entity.VideoItem;
import ua.meugen.android.popularmovies.model.db.execs.Executor;
import ua.meugen.android.popularmovies.model.db.execs.data.VideosData;
import ua.meugen.android.popularmovies.model.network.resp.VideosResponse;

/**
 * Created by meugen on 15.11.2017.
 */

public class MovieVideosActionApi extends OfflineFirstActionApi<Integer, List<VideoItem>> {

    @Inject ServerApi serverApi;
    @Inject VideosDao videosDao;
    @Inject Executor<VideosData> mergeVideosExecutor;
    @Inject KeyGenerator<Integer> keyGenerator;

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
        return serverApi.getMovieVideos(movieId).map(VideosResponse::getResults);
    }

    @Override
    void storeOffline(final Integer movieId, final List<VideoItem> videos) {
        final Disposable disposable = mergeVideosExecutor
                .executeTransaction(new VideosData(videos, movieId))
                .subscribe();
        getCompositeDisposable().add(disposable);
    }

    @NonNull
    @Override
    String cacheKey(final Integer movieId) {
        return keyGenerator.generateKey(movieId);
    }
}

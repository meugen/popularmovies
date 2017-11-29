package ua.meugen.android.popularmovies.model.api.actions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import ua.meugen.android.popularmovies.model.api.ServerApi;
import ua.meugen.android.popularmovies.model.cache.Cache;
import ua.meugen.android.popularmovies.model.cache.KeyGenerator;
import ua.meugen.android.popularmovies.model.config.Config;
import ua.meugen.android.popularmovies.model.db.dao.VideosDao;
import ua.meugen.android.popularmovies.model.db.entity.VideoItem;
import ua.meugen.android.popularmovies.model.db.execs.Executor;
import ua.meugen.android.popularmovies.model.db.execs.data.VideosData;
import ua.meugen.android.popularmovies.model.network.resp.VideosResponse;

/**
 * Created by meugen on 15.11.2017.
 */

public class MovieVideosActionApi extends OfflineFirstActionApi<Integer, List<VideoItem>> {

    @Inject Cache cache;
    @Inject Config config;
    @Inject ServerApi serverApi;
    @Inject VideosDao videosDao;
    @Inject Executor<VideosData> mergeVideosExecutor;
    @Inject KeyGenerator<Integer> keyGenerator;

    @Inject
    MovieVideosActionApi() {}

    @NonNull
    @Override
    Single<List<VideoItem>> offlineData(final Integer movieId) {
        return Single.fromCallable(() -> videosDao.byMovieId(movieId))
                .flatMap(this::checkEmpty);
    }

    private Single<List<VideoItem>> checkEmpty(final List<VideoItem> videos) {
        if (videos.isEmpty()) {
            throw new IllegalArgumentException("Offline data is empty");
        }
        return Single.just(videos);
    }

    @NonNull
    @Override
    Single<List<VideoItem>> networkData(final Integer movieId) {
        return serverApi.getMovieVideos(movieId, config.getLanguage())
                .map(VideosResponse::getResults);
    }

    private List<VideoItem> checkResponse(final VideosResponse response) {
        if (!response.isSuccess()) {
            throw new IllegalArgumentException("Not success: code = "
                    + response.getStatusCode() + ", message = " + response.getStatusMessage());
        }
        return response.getResults();
    }

    @Override
    void storeOffline(final Integer movieId, final List<VideoItem> videos) {
        final Disposable disposable = mergeVideosExecutor
                .executeTransaction(new VideosData(videos, movieId))
                .subscribe();
        getCompositeDisposable().add(disposable);
    }

    @Override
    public void clearCache(final Integer movieId) {
        cache.clear(keyGenerator.generateKey(movieId));
    }

    @NonNull
    @Override
    List<VideoItem> retrieveCache(final Integer movieId) {
        List<VideoItem> videos = cache.get(keyGenerator.generateKey(movieId));
        if (videos == null) {
            throw new IllegalArgumentException("No cache for videos for movie with id "
                    + movieId);
        }
        return videos;
    }

    @Override
    void storeCache(final Integer movieId, final List<VideoItem> videos) {
        cache.set(keyGenerator.generateKey(movieId), videos);
    }
}

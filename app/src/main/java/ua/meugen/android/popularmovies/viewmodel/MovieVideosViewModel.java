package ua.meugen.android.popularmovies.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import ua.meugen.android.popularmovies.model.api.ModelApi;
import ua.meugen.android.popularmovies.model.dto.VideoItemDto;
import ua.meugen.android.popularmovies.model.dto.VideosDto;
import ua.meugen.android.popularmovies.view.adapters.VideosAdapter;
import ua.meugen.android.popularmovies.viewmodel.listeners.OnClickVideoListener;

public class MovieVideosViewModel implements OnClickVideoListener {

    private static final String TAG = MovieVideosViewModel.class.getSimpleName();

    private static final String PARAM_MOVIE_ID = "movieId";

    public static void bindMovieId(final Bundle args, final int movieId) {
        args.putInt(PARAM_MOVIE_ID, movieId);
    }

    public final VideosAdapter adapter;

    private final Context context;
    private final ModelApi modelApi;
    private final CompositeSubscription compositeSubscription;

    private int movieId;

    @Inject
    public MovieVideosViewModel(
            final Context context,
            final ModelApi modelApi) {
        this.context = context;
        this.modelApi = modelApi;

        this.adapter = new VideosAdapter(context, this);
        this.compositeSubscription = new CompositeSubscription();
    }

    public void restoreInstanceState(final Bundle state) {
        movieId = state.getInt(PARAM_MOVIE_ID);
    }

    public void saveInstanceState(final Bundle outState) {
        outState.putInt(PARAM_MOVIE_ID, movieId);
    }

    public void load() {
        Subscription subscription = modelApi.movieVideos(movieId)
                .map(VideosDto::getResults)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onVideosSuccess, this::onVideosError);
        compositeSubscription.add(subscription);
    }

    private void onVideosSuccess(final List<VideoItemDto> videos) {
        adapter.setVideos(videos);
    }

    private void onVideosError(final Throwable th) {
        Log.e(TAG, th.getMessage(), th);
    }

    public void reset() {
        if (!compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
        }
    }

    @Override
    public void onClick(final VideoItemDto dto) {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.youtube.com/watch?v=" + dto.getKey()));
        context.startActivity(intent);
    }
}

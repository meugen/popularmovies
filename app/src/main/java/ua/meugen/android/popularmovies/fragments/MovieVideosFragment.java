package ua.meugen.android.popularmovies.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

import ua.meugen.android.popularmovies.adapters.VideosAdapter;
import ua.meugen.android.popularmovies.databinding.FragmentMovieVideosBinding;
import ua.meugen.android.popularmovies.dto.VideoItemDto;
import ua.meugen.android.popularmovies.dto.VideosDto;
import ua.meugen.android.popularmovies.loaders.AbstractCallbacks;
import ua.meugen.android.popularmovies.loaders.LoaderResult;
import ua.meugen.android.popularmovies.loaders.MovieVideosLoader;

/**
 * @author meugen
 */

public class MovieVideosFragment extends Fragment implements VideosAdapter.OnClickVideoListener {

    private static final String PARAM_ID = "id";
    private static final String PARAM_VIDEOS = "videos";

    public static MovieVideosFragment newInstance(final int id) {
        final Bundle arguments = new Bundle();
        arguments.putInt(PARAM_ID, id);

        final MovieVideosFragment fragment = new MovieVideosFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    private final VideosLoaderCallbacks callbacks
            = new VideosLoaderCallbacks();

    private int id;
    private VideosDto videos;

    private FragmentMovieVideosBinding binding;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            restoreInstanceState(getArguments());
        } else {
            restoreInstanceState(savedInstanceState);
        }
    }

    @Nullable
    @Override
    public View onCreateView(
            final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        binding = FragmentMovieVideosBinding.inflate(
                inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (videos == null) {
            loadVideos(false);
        } else {
            setupVideos();
        }
    }

    private void restoreInstanceState(final Bundle state) {
        id = state.getInt(PARAM_ID);
        videos = state.getParcelable(PARAM_VIDEOS);
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(PARAM_ID, id);
        outState.putParcelable(PARAM_VIDEOS, videos);
    }

    private void loadVideos(final boolean restart) {
        final LoaderManager loaderManager = getLoaderManager();
        final Bundle params = MovieVideosLoader.buildParams(id);
        if (restart) {
            loaderManager.restartLoader(0, params, callbacks);
        } else {
            loaderManager.initLoader(0, params, callbacks);
        }
    }

    private void setupVideos() {
        final VideosAdapter adapter = new VideosAdapter(getContext(),
                videos.getResults());
        adapter.setOnClickVideoListener(this);
        binding.videos.setAdapter(adapter);
    }

    @Override
    public void onClickListener(final VideoItemDto dto) {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.youtube.com/watch?v=" + dto.getKey()));
        startActivity(intent);
    }

    private class VideosLoaderCallbacks extends AbstractCallbacks<VideosDto> {

        @Override
        protected void onData(final VideosDto data) {
            videos = data;
            setupVideos();
        }

        @Override
        protected void onServerError(final String message, final int code) {}

        @Override
        protected void onNetworkError(final IOException ex) {}

        @Override
        protected void onNoNetwork() {}

        @Override
        public Loader<LoaderResult<VideosDto>> onCreateLoader(final int id, final Bundle args) {
            return new MovieVideosLoader(getContext(), args);
        }
    }
}

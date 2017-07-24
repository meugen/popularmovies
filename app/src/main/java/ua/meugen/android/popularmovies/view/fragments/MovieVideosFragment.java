package ua.meugen.android.popularmovies.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.PopularMovies;
import ua.meugen.android.popularmovies.view.adapters.VideosAdapter;
import ua.meugen.android.popularmovies.databinding.FragmentMovieVideosBinding;
import ua.meugen.android.popularmovies.model.dto.VideoItemDto;
import ua.meugen.android.popularmovies.model.dto.VideosDto;
import ua.meugen.android.popularmovies.loaders.AbstractCallbacks;
import ua.meugen.android.popularmovies.loaders.LoaderResult;
import ua.meugen.android.popularmovies.loaders.MovieVideosLoader;
import ua.meugen.android.popularmovies.viewmodel.MovieVideosViewModel;
import ua.meugen.android.popularmovies.viewmodel.listeners.OnClickVideoListener;

/**
 * @author meugen
 */

public class MovieVideosFragment extends Fragment implements OnClickVideoListener {

    public static MovieVideosFragment newInstance(final int id) {
        final Bundle arguments = new Bundle();
        MovieVideosViewModel.bindMovieId(arguments, id);

        final MovieVideosFragment fragment = new MovieVideosFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Inject MovieVideosViewModel model;

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        PopularMovies.appComponent(context).inject(this);
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            model.restoreInstanceState(getArguments());
        } else {
            model.restoreInstanceState(savedInstanceState);
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
        model.load();
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        model.saveInstanceState(outState);
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
            final MovieVideosLoader loader = PopularMovies
                    .loadersComponent(getContext())
                    .movieVideosLoader();
            loader.attachParams(args);
            return loader;
        }
    }
}

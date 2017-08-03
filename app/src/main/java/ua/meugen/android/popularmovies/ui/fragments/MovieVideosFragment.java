package ua.meugen.android.popularmovies.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.app.PopularMovies;
import ua.meugen.android.popularmovies.model.responses.VideoItemDto;
import ua.meugen.android.popularmovies.presenter.MovieVideosPresenter;
import ua.meugen.android.popularmovies.presenter.listeners.OnClickVideoListener;
import ua.meugen.android.popularmovies.ui.MovieVideosView;
import ua.meugen.android.popularmovies.ui.adapters.VideosAdapter;

/**
 * @author meugen
 */

public class MovieVideosFragment extends MvpFragment<MovieVideosView, MovieVideosPresenter>
        implements OnClickVideoListener, MovieVideosView {

    private static final String PARAM_MOVIE_ID = "movieId";

    public static MovieVideosFragment newInstance(final int id) {
        final Bundle arguments = new Bundle();
        arguments.putInt(PARAM_MOVIE_ID, id);

        final MovieVideosFragment fragment = new MovieVideosFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    private VideosAdapter adapter;

    @BindView(R.id.videos) RecyclerView videosView;

    @Nullable
    @Override
    public View onCreateView(
            final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(
                R.layout.fragment_movie_videos, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            restoreInstanceState(getArguments());
        } else {
            restoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.load();
    }

    private void restoreInstanceState(final Bundle state) {
        presenter.setMovieId(state.getInt(PARAM_MOVIE_ID));
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PARAM_MOVIE_ID, presenter.getMovieId());
    }

    @Override
    public MovieVideosPresenter createPresenter() {
        return PopularMovies.appComponent(getContext())
                .createMovieVideosPresenter();
    }

    @Override
    public void onClickVideo(final VideoItemDto dto) {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.youtube.com/watch?v=" + dto.getKey()));
        startActivity(intent);
    }

    @Override
    public void onVideosLoaded(final List<VideoItemDto> videos) {
        if (adapter == null) {
            adapter = new VideosAdapter(getContext(), this);
            videosView.setAdapter(adapter);
        }
        adapter.setVideos(videos);
    }
}

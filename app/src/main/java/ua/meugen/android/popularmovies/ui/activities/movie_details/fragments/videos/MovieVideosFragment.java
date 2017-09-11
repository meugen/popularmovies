package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.videos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ua.meugen.android.popularmovies.databinding.FragmentMovieVideosBinding;
import ua.meugen.android.popularmovies.model.responses.VideoItemDto;
import ua.meugen.android.popularmovies.presenter.listeners.OnClickVideoListener;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.BaseFragment;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.videos.presenter.MovieVideosPresenterImpl;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.videos.state.MovieVideosState;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.videos.view.MovieVideosView;
import ua.meugen.android.popularmovies.ui.adapters.VideosAdapter;

/**
 * @author meugen
 */

public class MovieVideosFragment extends BaseFragment<MovieVideosState, MovieVideosPresenterImpl>
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
    private FragmentMovieVideosBinding binding;

    @Nullable
    @Override
    public View onCreateView(
            final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        binding = FragmentMovieVideosBinding.inflate(inflater, container, false);
        return binding.getRoot();
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
            binding.videos.setAdapter(adapter);
        }
        adapter.setVideos(videos);
    }
}

package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.videos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.databinding.FragmentMovieVideosBinding;
import ua.meugen.android.popularmovies.model.db.entity.VideoItem;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.BaseFragment;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.videos.adapters.OnClickVideoListener;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.videos.adapters.VideosAdapter;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.videos.presenter.MovieVideosPresenterImpl;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.videos.state.MovieVideosState;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.videos.view.MovieVideosView;

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

    @Inject VideosAdapter adapter;

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
    public void onViewCreated(
            final View view,
            @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.videos.setAdapter(adapter);
    }

    @Override
    public void onClickVideo(final VideoItem dto) {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.youtube.com/watch?v=" + dto.key));
        startActivity(intent);
    }

    @Override
    public void onVideosLoaded(final List<VideoItem> videos) {
        adapter.swapVideos(videos);
    }
}

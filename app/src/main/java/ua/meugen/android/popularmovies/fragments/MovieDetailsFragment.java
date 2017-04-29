package ua.meugen.android.popularmovies.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.meugen.android.popularmovies.databinding.FragmentMovieDetailsBinding;
import ua.meugen.android.popularmovies.dto.MovieItemDto;


public class MovieDetailsFragment extends Fragment {

    private static final String PARAM_MOVIE = "movie";

    public static MovieDetailsFragment newInstance(final MovieItemDto movie) {
        final Bundle arguments = new Bundle();
        arguments.putParcelable(PARAM_MOVIE, movie);

        final MovieDetailsFragment fragment = new MovieDetailsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    private MovieItemDto movie;

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
        final FragmentMovieDetailsBinding binding = FragmentMovieDetailsBinding
                .inflate(inflater, container, false);
        binding.setMovie(movie);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(movie.getTitle());
    }

    private void restoreInstanceState(final Bundle state) {
        this.movie = state.getParcelable(PARAM_MOVIE);
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(PARAM_MOVIE, this.movie);
    }
}

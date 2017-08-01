package ua.meugen.android.popularmovies.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.PopularMovies;
import ua.meugen.android.popularmovies.databinding.FragmentMovieVideosBinding;
import ua.meugen.android.popularmovies.presenter.MovieVideosPresenter;

/**
 * @author meugen
 */

public class MovieVideosFragment extends Fragment {

    public static MovieVideosFragment newInstance(final int id) {
        final Bundle arguments = new Bundle();
        MovieVideosPresenter.bindMovieId(arguments, id);

        final MovieVideosFragment fragment = new MovieVideosFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Inject
    MovieVideosPresenter model;

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
        FragmentMovieVideosBinding binding = FragmentMovieVideosBinding
                .inflate(inflater, container, false);
        binding.setModel(model);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model.load();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        model.reset();
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        model.saveInstanceState(outState);
    }
}

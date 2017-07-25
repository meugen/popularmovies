package ua.meugen.android.popularmovies.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.PopularMovies;
import ua.meugen.android.popularmovies.databinding.FragmentMovieReviewsBinding;
import ua.meugen.android.popularmovies.model.dto.ReviewItemDto;
import ua.meugen.android.popularmovies.viewmodel.MovieReviewsViewModel;

/**
 * @author meugen
 */

public class MovieReviewsFragment extends Fragment implements Observer {

    public static MovieReviewsFragment newInstance(final int id) {
        final Bundle arguments = new Bundle();
        MovieReviewsViewModel.bindMovieId(arguments, id);

        final MovieReviewsFragment fragment = new MovieReviewsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Inject MovieReviewsViewModel model;

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
        model.addObserver(this);
    }

    @Nullable
    @Override
    public View onCreateView(
            final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        FragmentMovieReviewsBinding binding = FragmentMovieReviewsBinding
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
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        model.saveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        model.reset();
        model.deleteObserver(this);
    }

    @Override
    public void update(final Observable observable, final Object o) {
        if (o instanceof ReviewItemDto) {
            model.showReview(getFragmentManager(), (ReviewItemDto) o);
        }
    }
}

package ua.meugen.android.popularmovies.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.PopularMovies;
import ua.meugen.android.popularmovies.view.activities.AuthorizeActivity;
import ua.meugen.android.popularmovies.app.ListenersCollector;
import ua.meugen.android.popularmovies.databinding.FragmentMovieDetailsBinding;
import ua.meugen.android.popularmovies.viewmodel.MovieDetailsViewModel;


public class MovieDetailsFragment extends Fragment
        implements Observer {

    public static MovieDetailsFragment newInstance(final int movieId) {
        final Bundle arguments = new Bundle();
        MovieDetailsViewModel.bindMovieId(arguments, movieId);

        final MovieDetailsFragment fragment = new MovieDetailsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Inject MovieDetailsViewModel model;

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
        final ListenersCollector listenersCollector
                = ListenersCollector.from(getActivity());
        model.registerListeners(listenersCollector);
        model.addObserver(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        final ListenersCollector listenersCollector
                = ListenersCollector.from(getActivity());
        model.reset(listenersCollector);
        model.deleteObserver(this);
    }

    @Nullable
    @Override
    public View onCreateView(
            final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        FragmentMovieDetailsBinding binding = FragmentMovieDetailsBinding
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
    public void onActivityResult(
            final int requestCode,
            final int resultCode,
            final Intent data) {
        if (resultCode == AuthorizeActivity.RESULT_OK) {
            final String session = data.getStringExtra(AuthorizeActivity.EXTRA_SESSION);
            final PopularMovies popularMovies = PopularMovies.from(getContext());
            popularMovies.storeSession(session, false, new Date(Long.MAX_VALUE));
            model.rateMovieWithSession();
        } else {
            Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void update(final Observable observable, final Object o) {
        if (MovieDetailsViewModel.ACTION_SELECT_SESSION.equals(o)) {
            model.selectSessionType(getFragmentManager());
        } else if (MovieDetailsViewModel.ACTION_RATE_MOVIE.equals(o)) {
            model.rateMovieWithSession(getFragmentManager());
        } else if (MovieDetailsViewModel.ACTION_AUTH_USER_SESSION.equals(o)) {
            Intent intent = new Intent(getContext(), AuthorizeActivity.class);
            startActivityForResult(intent, 0);
        } else if (o instanceof CharSequence) {
            Toast.makeText(getContext(), (CharSequence) o, Toast.LENGTH_LONG).show();
        }
    }
}

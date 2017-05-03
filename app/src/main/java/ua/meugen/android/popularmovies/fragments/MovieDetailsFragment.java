package ua.meugen.android.popularmovies.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import ua.meugen.android.popularmovies.PopularMovies;
import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.activities.AuthorizeActivity;
import ua.meugen.android.popularmovies.app.ListenersCollector;
import ua.meugen.android.popularmovies.app.Session;
import ua.meugen.android.popularmovies.databinding.FragmentMovieDetailsBinding;
import ua.meugen.android.popularmovies.dialogs.SelectSessionTypeDialog;
import ua.meugen.android.popularmovies.dto.MovieItemDto;
import ua.meugen.android.popularmovies.dto.NewGuestSessionDto;
import ua.meugen.android.popularmovies.loaders.AbstractCallbacks;
import ua.meugen.android.popularmovies.loaders.LoaderResult;
import ua.meugen.android.popularmovies.loaders.NewGuestSessionLoader;
import ua.meugen.android.popularmovies.utils.BundleUtils;


public class MovieDetailsFragment extends Fragment
        implements View.OnClickListener, SelectSessionTypeDialog.OnSessionTypeSelectedListener {

    private static final String PARAM_MOVIE = "movie";
    private static final String PARAM_SELECT_SESSION_LISTENER_UUID
            = "selectSessionListenerUUID";

    private static final int NEW_GUEST_SESSION_LOADER_ID = 1;

    public static MovieDetailsFragment newInstance(final MovieItemDto movie) {
        final Bundle arguments = new Bundle();
        arguments.putParcelable(PARAM_MOVIE, movie);

        final MovieDetailsFragment fragment = new MovieDetailsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    private final NewGuestSessionCallbacks guestSessionCallbacks
            = new NewGuestSessionCallbacks();

    private MovieItemDto movie;
    private UUID selectSessionListenerUUID;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            restoreInstanceState(getArguments());
        } else {
            restoreInstanceState(savedInstanceState);
        }
        final ListenersCollector listenersCollector
                = ListenersCollector.from(getActivity());
        selectSessionListenerUUID = listenersCollector
                .registerListener(selectSessionListenerUUID, this);
    }

    @Override
    public void onDestroy() {
        final ListenersCollector listenersCollector
                = ListenersCollector.from(getActivity());
        listenersCollector.unregisterListener(selectSessionListenerUUID);
        super.onDestroy();
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
        binding.rateMovie.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(movie.getTitle());
    }

    private void restoreInstanceState(final Bundle state) {
        this.movie = state.getParcelable(PARAM_MOVIE);
        this.selectSessionListenerUUID = BundleUtils.getUUID(state,
                PARAM_SELECT_SESSION_LISTENER_UUID);
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(PARAM_MOVIE, this.movie);
        BundleUtils.putUUID(outState, PARAM_SELECT_SESSION_LISTENER_UUID,
                selectSessionListenerUUID);
    }

    @Override
    public void onClick(final View view) {
        final int viewId = view.getId();
        if (viewId == R.id.rate_movie) {
            rateMovie();
        }
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
            rateMovieWithSession(popularMovies.getSession());
        } else {
            Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUserSessionSelected() {
        final Intent intent = new Intent(getContext(), AuthorizeActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onGuestSessionSelected() {
        Toast.makeText(getContext(), "onGuestSessionSelected()",
                Toast.LENGTH_LONG).show();
    }

    private void rateMovie() {
        final Session session = PopularMovies.from(getContext()).getSession();
        if (session == null) {
            final SelectSessionTypeDialog dialog = SelectSessionTypeDialog
                    .newInstance(selectSessionListenerUUID);
            dialog.show(getFragmentManager(), "select_session_type");
        } else {
            rateMovieWithSession(session);
        }
    }

    private void rateMovieWithSession(final Session session) {
        Toast.makeText(getContext(), "rateMovieWithSession("
                + session + ")", Toast.LENGTH_LONG).show();
    }

    private class NewGuestSessionCallbacks extends AbstractCallbacks<NewGuestSessionDto> {

        @Override
        protected void onData(final NewGuestSessionDto data) {

        }

        @Override
        protected void onServerError(final String message, final int code) {

        }

        @Override
        protected void onNetworkError(final IOException ex) {

        }

        @Override
        protected void onNoNetwork() {

        }

        @Override
        public Loader<LoaderResult<NewGuestSessionDto>> onCreateLoader(final int id, final Bundle args) {
            return new NewGuestSessionLoader(getContext());
        }
    }
}

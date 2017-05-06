package ua.meugen.android.popularmovies.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
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
import ua.meugen.android.popularmovies.dialogs.RateMovieDialog;
import ua.meugen.android.popularmovies.dialogs.SelectSessionTypeDialog;
import ua.meugen.android.popularmovies.dto.BaseDto;
import ua.meugen.android.popularmovies.dto.MovieItemDto;
import ua.meugen.android.popularmovies.dto.NewGuestSessionDto;
import ua.meugen.android.popularmovies.loaders.AbstractCallbacks;
import ua.meugen.android.popularmovies.loaders.LoaderResult;
import ua.meugen.android.popularmovies.loaders.NewGuestSessionLoader;
import ua.meugen.android.popularmovies.loaders.RateMovieLoader;
import ua.meugen.android.popularmovies.providers.MoviesContract;
import ua.meugen.android.popularmovies.utils.BundleUtils;


public class MovieDetailsFragment extends Fragment
        implements View.OnClickListener,
        SelectSessionTypeDialog.OnSessionTypeSelectedListener,
        RateMovieDialog.OnMovieRatedListener {

    private static final String PARAM_MOVIE_ID = "movieId";
    private static final String PARAM_LISTENER_UUID
            = "listenerUUID";
    private static final String PARAM_ACTIVE_LOADER = "activeLoader";

    private static final int NEW_GUEST_SESSION_LOADER_ID = 1;
    private static final int RATE_MOVIE_LOADER_ID = 2;
    private static final int MOVIE_DETAILS_LOADER = 3;

    public static MovieDetailsFragment newInstance(final int movieId) {
        final Bundle arguments = new Bundle();
        arguments.putInt(PARAM_MOVIE_ID, movieId);

        final MovieDetailsFragment fragment = new MovieDetailsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    private final NewGuestSessionCallbacks guestSessionCallbacks
            = new NewGuestSessionCallbacks();
    private final RateMovieCallbacks rateMovieCallbacks
            = new RateMovieCallbacks();
    private final MovieDetailsCallbacks movieDetailsCallbacks
            = new MovieDetailsCallbacks();

    private FragmentMovieDetailsBinding binding;

    private int movieId;
    private UUID listenerUUID;
    private int activeLoader = -1;

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
        listenerUUID = listenersCollector
                .registerListener(listenerUUID, this);
    }

    @Override
    public void onDestroy() {
        final ListenersCollector listenersCollector
                = ListenersCollector.from(getActivity());
        listenersCollector.unregisterListener(listenerUUID);
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(
            final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        binding = FragmentMovieDetailsBinding
                .inflate(inflater, container, false);
        binding.rateMovie.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(MOVIE_DETAILS_LOADER,
                null, movieDetailsCallbacks);
        if (activeLoader == NEW_GUEST_SESSION_LOADER_ID) {
            getLoaderManager().initLoader(NEW_GUEST_SESSION_LOADER_ID,
                    null, guestSessionCallbacks);
        } else if (activeLoader == RATE_MOVIE_LOADER_ID) {
            getLoaderManager().initLoader(RATE_MOVIE_LOADER_ID,
                    null, rateMovieCallbacks);
        }
    }

    private void restoreInstanceState(final Bundle state) {
        this.movieId = state.getInt(PARAM_MOVIE_ID);
        this.listenerUUID = BundleUtils.getUUID(state,
                PARAM_LISTENER_UUID);
        this.activeLoader = state.getInt(
                PARAM_ACTIVE_LOADER);
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PARAM_MOVIE_ID, this.movieId);
        BundleUtils.putUUID(outState, PARAM_LISTENER_UUID,
                listenerUUID);
        outState.putInt(PARAM_ACTIVE_LOADER, activeLoader);
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
            rateMovieWithSession();
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
        activeLoader = NEW_GUEST_SESSION_LOADER_ID;
        getLoaderManager().initLoader(NEW_GUEST_SESSION_LOADER_ID,
                null, guestSessionCallbacks);
    }

    private void rateMovie() {
        final Session session = PopularMovies.from(getContext()).getSession();
        if (session == null) {
            final SelectSessionTypeDialog dialog = SelectSessionTypeDialog
                    .newInstance(listenerUUID);
            dialog.show(getFragmentManager(), "select_session_type");
        } else {
            rateMovieWithSession();
        }
    }

    private void rateMovieWithSession() {
        final RateMovieDialog dialog = RateMovieDialog
                .newInstance(listenerUUID);
        dialog.show(getFragmentManager(), "rate_movie");
    }

    @Override
    public void onMovieRated(final float value) {
        activeLoader = RATE_MOVIE_LOADER_ID;
        final Bundle args = RateMovieLoader.buildParams(movieId, value);
        getLoaderManager().restartLoader(RATE_MOVIE_LOADER_ID, args, rateMovieCallbacks);
    }

    private void showMessage(final CharSequence message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private class NewGuestSessionCallbacks extends AbstractCallbacks<NewGuestSessionDto> {

        @Override
        protected void onData(final NewGuestSessionDto data) {
            final PopularMovies popularMovies = PopularMovies.from(getContext());
            popularMovies.storeSession(data.getGuestSessionId(), true, data.getExpiresAt());
            rateMovieWithSession();
        }

        @Override
        protected void onServerError(final String message, final int code) {
            showMessage(getString(R.string.guest_session_result_server_error, message));
        }

        @Override
        protected void onNetworkError(final IOException ex) {
            showMessage(getString(R.string.guest_session_result_network_error));
        }

        @Override
        protected void onNoNetwork() {
            showMessage(getString(R.string.guest_session_result_no_network));
        }

        @Override
        public Loader<LoaderResult<NewGuestSessionDto>> onCreateLoader(final int id, final Bundle args) {
            return new NewGuestSessionLoader(getContext());
        }

        @Override
        protected void onCompleted() {
            activeLoader = -1;
        }
    }

    private class RateMovieCallbacks extends AbstractCallbacks<BaseDto> {

        @Override
        protected void onData(final BaseDto data) {
            showMessage(getText(R.string.rate_movie_result_ok));
        }

        @Override
        protected void onServerError(final String message, final int code) {
            showMessage(getString(R.string.rate_movie_result_server_error, message));
        }

        @Override
        protected void onNetworkError(final IOException ex) {
            showMessage(getText(R.string.rate_movie_result_network_error));
        }

        @Override
        protected void onNoNetwork() {
            showMessage(getText(R.string.rate_movie_result_no_network));
        }

        @Override
        public Loader<LoaderResult<BaseDto>> onCreateLoader(final int id, final Bundle args) {
            return new RateMovieLoader(getContext(), args);
        }

        @Override
        protected void onCompleted() {
            activeLoader = -1;
        }
    }

    private class MovieDetailsCallbacks implements LoaderManager.LoaderCallbacks<Cursor>, MoviesContract {

        @Override
        public Loader<Cursor> onCreateLoader(final int id, final Bundle args) {
            final Uri uri = MOVIES_URI.buildUpon()
                    .appendPath(Integer.toString(movieId))
                    .build();
            final String[] columns = new String[] {
                    FIELD_TITLE,
                    FIELD_POSTER_PATH,
                    FIELD_RELEASE_DATE,
                    FIELD_VOTE_AVERAGE,
                    FIELD_OVERVIEW };
            return new CursorLoader(getContext(), uri, columns, null, null, null);
        }

        @Override
        public void onLoadFinished(final Loader<Cursor> loader, final Cursor data) {
            data.moveToFirst();
            getActivity().setTitle(data.getString(0));
            binding.setCursor(data);
        }

        @Override
        public void onLoaderReset(final Loader<Cursor> loader) {}
    }
}

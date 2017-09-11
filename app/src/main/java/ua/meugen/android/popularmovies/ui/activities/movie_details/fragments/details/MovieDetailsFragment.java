package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.DateFormat;

import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.databinding.FragmentMovieDetailsBinding;
import ua.meugen.android.popularmovies.model.responses.MovieItemDto;
import ua.meugen.android.popularmovies.presenter.images.FileSize;
import ua.meugen.android.popularmovies.presenter.images.ImageLoader;
import ua.meugen.android.popularmovies.ui.activities.authorize.AuthorizeActivity;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.BaseFragment;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details.presenter.MovieDetailsPresenter;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details.state.MovieDetailsState;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details.view.MovieDetailsView;
import ua.meugen.android.popularmovies.ui.dialogs.RateMovieDialog;
import ua.meugen.android.popularmovies.ui.dialogs.SelectSessionTypeDialog;
import ua.meugen.android.popularmovies.ui.helpers.ListenersCollector;


public class MovieDetailsFragment extends BaseFragment<MovieDetailsState, MovieDetailsPresenter>
        implements MovieDetailsView, RateMovieDialog.OnMovieRatedListener,
        SelectSessionTypeDialog.OnSessionTypeSelectedListener {

    private static final String PARAM_MOVIE_ID = "movieId";

    public static MovieDetailsFragment newInstance(final int movieId) {
        final Bundle arguments = new Bundle();
        arguments.putInt(PARAM_MOVIE_ID, movieId);

        final MovieDetailsFragment fragment = new MovieDetailsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    private FragmentMovieDetailsBinding binding;

    @Override
    public void onDestroy() {
        super.onDestroy();
        final ListenersCollector listenersCollector
                = ListenersCollector.from(getActivity());
        listenersCollector.unregisterListener(presenter.getListenerUUID());
    }

    @Nullable
    @Override
    public View onCreateView(
            final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        binding = FragmentMovieDetailsBinding.inflate(
                inflater, container, false);
        binding.setPresenter(presenter);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListenersCollector collector = ListenersCollector.from(this.getActivity());
        presenter.setListenerUUID(collector.registerListener(presenter.getListenerUUID(), this));
    }

    @Override
    public void onActivityResult(
            final int requestCode,
            final int resultCode,
            final Intent data) {
        if (resultCode == AuthorizeActivity.RESULT_OK) {
            final String session = data.getStringExtra(AuthorizeActivity.EXTRA_SESSION);
            presenter.storeUserSession(session);
            rateMovieWithSession();
        } else {
            Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void gotMovie(final MovieItemDto movie) {
        ImageLoader.from(getContext())
                .load(FileSize.w(500), movie.getPosterPath())
                .into(binding.poster);
        binding.releaseDate.setText(DateFormat.getDateInstance()
                .format(movie.getReleaseDate()));
        binding.voteAverage.setText(getString(R.string.activity_movie_details_vote_average,
                movie.getVoteAverage()));
        binding.overview.setText(movie.getOverview());
    }

    @Override
    public void onUserSessionSelected() {
        final Context context = getContext();
        final Intent intent = new Intent(context, AuthorizeActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onGuestSessionSelected() {
        presenter.createGuestSession();
    }

    @Override
    public void onMovieRated(final float value) {
        presenter.onMovieRated(value);
    }

    @Override
    public void selectSession() {
        final SelectSessionTypeDialog dialog = SelectSessionTypeDialog
                .newInstance(presenter.getListenerUUID());
        dialog.show(getFragmentManager(), "select_session_type");
    }

    @Override
    public void rateMovieWithSession() {
        final RateMovieDialog dialog = RateMovieDialog
                .newInstance(presenter.getListenerUUID());
        dialog.show(getFragmentManager(), "rate_movie");
    }

    @Override
    public void onMovieRatedSuccess() {
        showMessage(getText(R.string.result_rate_movie_ok));
    }

    @Override
    public void onServerError(final String message) {
        showMessage(getString(R.string.result_server_error, message));
    }

    @Override
    public void onError() {
        showMessage(getText(R.string.result_network_error));
    }

    private void showMessage(final CharSequence message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}

package ua.meugen.android.popularmovies.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.text.DateFormat;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.app.PopularMovies;
import ua.meugen.android.popularmovies.model.responses.MovieItemDto;
import ua.meugen.android.popularmovies.presenter.MovieDetailsPresenter;
import ua.meugen.android.popularmovies.presenter.images.FileSize;
import ua.meugen.android.popularmovies.presenter.images.ImageLoader;
import ua.meugen.android.popularmovies.ui.MovieDetailsView;
import ua.meugen.android.popularmovies.ui.activities.AuthorizeActivity;
import ua.meugen.android.popularmovies.ui.dialogs.RateMovieDialog;
import ua.meugen.android.popularmovies.ui.dialogs.SelectSessionTypeDialog;
import ua.meugen.android.popularmovies.ui.helpers.ListenersCollector;
import ua.meugen.android.popularmovies.ui.utils.BundleUtils;


public class MovieDetailsFragment extends MvpFragment<MovieDetailsView, MovieDetailsPresenter>
        implements MovieDetailsView, RateMovieDialog.OnMovieRatedListener,
        SelectSessionTypeDialog.OnSessionTypeSelectedListener {

    private static final String PARAM_MOVIE_ID = "movieId";
    private static final String PARAM_LISTENER_UUID
            = "listenerUUID";

    public static MovieDetailsFragment newInstance(final int movieId) {
        final Bundle arguments = new Bundle();
        arguments.putInt(PARAM_MOVIE_ID, movieId);

        final MovieDetailsFragment fragment = new MovieDetailsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @BindView(R.id.poster) ImageView posterView;
    @BindView(R.id.release_date) TextView releaseDateView;
    @BindView(R.id.vote_average) TextView voteAverageView;
    @BindView(R.id.overview) TextView overviewView;

    private UUID listenerUUID;

    @Override
    @NonNull
    public MovieDetailsPresenter createPresenter() {
        return PopularMovies.appComponent(getContext())
                .createMovieDetailsPresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        final ListenersCollector listenersCollector
                = ListenersCollector.from(getActivity());
        listenersCollector.unregisterListener(listenerUUID);
    }

    @Nullable
    @Override
    public View onCreateView(
            final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_movie_details,
                container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            restoreInstanceState(getArguments());
        } else {
            restoreInstanceState(savedInstanceState);
        }
        presenter.load();
    }

    private void restoreInstanceState(final Bundle state) {
        presenter.setMovieId(state.getInt(PARAM_MOVIE_ID));
        listenerUUID = BundleUtils.getUUID(state, PARAM_LISTENER_UUID);
        ListenersCollector collector = ListenersCollector.from(getActivity());
        listenerUUID = collector.registerListener(listenerUUID, this);
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PARAM_MOVIE_ID, presenter.getMovieId());
        BundleUtils.putUUID(outState, PARAM_LISTENER_UUID, listenerUUID);
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
                .into(posterView);
        releaseDateView.setText(DateFormat.getDateInstance()
                .format(movie.getReleaseDate()));
        voteAverageView.setText(getString(R.string.activity_movie_details_vote_average,
                movie.getVoteAverage()));
        overviewView.setText(movie.getOverview());
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
                .newInstance(listenerUUID);
        dialog.show(getFragmentManager(), "select_session_type");
    }

    @Override
    public void rateMovieWithSession() {
        final RateMovieDialog dialog = RateMovieDialog
                .newInstance(listenerUUID);
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

    @OnClick(R.id.rate_movie)
    public void onRateMovie() {
        presenter.rateMovie();
    }

    @OnClick(R.id.switch_favorites)
    public void onSwitchFavorites() {
        presenter.switchFavorites();
    }
}

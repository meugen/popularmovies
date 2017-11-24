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

import javax.inject.Inject;
import javax.inject.Named;

import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.databinding.FragmentMovieDetailsBinding;
import ua.meugen.android.popularmovies.model.SortType;
import ua.meugen.android.popularmovies.model.db.entity.MovieItem;
import ua.meugen.android.popularmovies.ui.activities.authorize.AuthorizeActivity;
import ua.meugen.android.popularmovies.ui.activities.base.BaseActivityModule;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.BaseFragment;
import ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.rate.OnMovieRatedListener;
import ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.rate.RateMovieDialog;
import ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.session.OnSessionTypeSelectedListener;
import ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.session.SelectSessionTypeDialog;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details.presenter.MovieDetailsPresenter;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details.state.MovieDetailsState;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details.view.MovieDetailsView;
import ua.meugen.android.popularmovies.ui.utils.images.FileSize;
import ua.meugen.android.popularmovies.ui.utils.images.ImageLoader;


public class MovieDetailsFragment extends BaseFragment<MovieDetailsState, MovieDetailsPresenter>
        implements MovieDetailsView, OnMovieRatedListener,
        OnSessionTypeSelectedListener {

    public static MovieDetailsFragment newInstance(final int movieId) {
        final Bundle arguments = new Bundle();
        arguments.putInt(MovieDetailsState.PARAM_MOVIE_ID, movieId);

        final MovieDetailsFragment fragment = new MovieDetailsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Inject @Named(BaseActivityModule.ACTIVITY_CONTEXT)
    Context context;

    private FragmentMovieDetailsBinding binding;

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
    public void onStart() {
        super.onStart();
        presenter.load();
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
    public void gotMovie(final MovieItem movie) {
        ImageLoader.from(getContext())
                .load(FileSize.w(500), movie.posterPath)
                .into(binding.poster);
        binding.releaseDate.setText(DateFormat.getDateInstance()
                .format(movie.releaseDate));
        binding.voteAverage.setText(getString(R.string.activity_movie_details_vote_average,
                movie.voteAverage));
        binding.overview.setText(movie.overview);
        binding.switchFavorites.setChecked(
                (movie.status & SortType.FAVORITES) == SortType.FAVORITES);
    }

    @Override
    public void onUserSessionSelected() {
        final Intent intent = new Intent(context, AuthorizeActivity.class);
        startActivityForResult(intent, 0);
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
        new SelectSessionTypeDialog().show(
                getFragmentManager(), "select_session_type");
    }

    @Override
    public void rateMovieWithSession() {
        new RateMovieDialog().show(
                getFragmentManager(), "rate_movie");
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

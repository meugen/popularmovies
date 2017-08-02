package ua.meugen.android.popularmovies.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.Date;
import java.util.Observable;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.meugen.android.popularmovies.PopularMovies;
import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.model.Session;
import ua.meugen.android.popularmovies.model.responses.MovieItemDto;
import ua.meugen.android.popularmovies.presenter.MovieDetailsPresenter;
import ua.meugen.android.popularmovies.view.MovieDetailsView;
import ua.meugen.android.popularmovies.view.activities.AuthorizeActivity;
import ua.meugen.android.popularmovies.view.dialogs.RateMovieDialog;
import ua.meugen.android.popularmovies.view.dialogs.SelectSessionTypeDialog;
import ua.meugen.android.popularmovies.view.helpers.ListenersCollector;
import ua.meugen.android.popularmovies.view.utils.BundleUtils;


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
        listenerUUID = collector.registerListener(listenerUUID, presenter);
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
            final PopularMovies popularMovies = PopularMovies.from(getContext());
            popularMovies.storeSession(session, false, new Date(Long.MAX_VALUE));
            rateMovieWithSession();
        } else {
            Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void gotMovie(final MovieItemDto movie) {

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

    @OnClick(R.id.rate_movie)
    public void rateMovie() {
        presenter.rateMovie();
    }

    //    @Override
//    public void update(final Observable observable, final Object o) {
//        if (MovieDetailsPresenter.ACTION_SELECT_SESSION.equals(o)) {
//            model.selectSessionType(getFragmentManager());
//        } else if (MovieDetailsPresenter.ACTION_RATE_MOVIE.equals(o)) {
//            model.rateMovieWithSession(getFragmentManager());
//        } else if (MovieDetailsPresenter.ACTION_AUTH_USER_SESSION.equals(o)) {
//            Intent intent = new Intent(getContext(), AuthorizeActivity.class);
//            startActivityForResult(intent, 0);
//        } else if (o instanceof CharSequence) {
//            Toast.makeText(getContext(), (CharSequence) o, Toast.LENGTH_LONG).show();
//        }
//    }
}

package ua.meugen.android.popularmovies.view.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.RatingBar;

import java.util.UUID;

import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.databinding.DialogRateMovieBinding;
import ua.meugen.android.popularmovies.view.utils.BundleUtils;
import ua.meugen.android.popularmovies.view.ListenersCollector;

/**
 * @author meugen
 */

public class RateMovieDialog extends DialogFragment
        implements RatingBar.OnRatingBarChangeListener, DialogInterface.OnClickListener {

    private static final String PARAM_MOVIE_RATE = "movieRate";
    private static final String PARAM_LISTENER_UUID = "listenerUUID";

    public static RateMovieDialog newInstance(final UUID listenerUUID) {
        final Bundle arguments = new Bundle();
        BundleUtils.putUUID(arguments, PARAM_LISTENER_UUID, listenerUUID);

        final RateMovieDialog dialog = new RateMovieDialog();
        dialog.setArguments(arguments);
        return dialog;
    }

    private UUID listenerUUID;
    private float movieRate;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            restoreInstanceState(getArguments());
        } else {
            restoreInstanceState(savedInstanceState);
        }
    }

    private void restoreInstanceState(final Bundle state) {
        this.listenerUUID = BundleUtils.getUUID(state, PARAM_LISTENER_UUID);
        this.movieRate = state.getFloat(PARAM_MOVIE_RATE, 0);
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloat(PARAM_MOVIE_RATE, movieRate);
        BundleUtils.putUUID(outState, PARAM_LISTENER_UUID, listenerUUID);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final LayoutInflater inflater = LayoutInflater.from(getContext());
        final DialogRateMovieBinding binding = DialogRateMovieBinding.inflate(inflater);
        binding.setRating(movieRate);
        binding.movieRate.setOnRatingBarChangeListener(this);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.rate_movie_text);
        builder.setView(binding.getRoot());
        builder.setNegativeButton(R.string.button_cancel, this);
        builder.setPositiveButton(R.string.button_ok, this);
        return builder.create();
    }

    @Override
    public void onRatingChanged(
            final RatingBar ratingBar,
            final float rating,
            final boolean fromUser) {
        movieRate = rating;
    }

    @Override
    public void onClick(final DialogInterface dialogInterface, final int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            callMovieRatedListener();
        }
    }

    private void callMovieRatedListener() {
        final ListenersCollector collector = ListenersCollector.from(getActivity());
        final OnMovieRatedListener listener = collector.retrieveListener(listenerUUID);
        if (listener != null) {
            final Resources resources = getContext().getResources();
            final int numStars = resources.getInteger(R.integer.rate_movie_num_stars);
            final int maxValue = resources.getInteger(R.integer.rate_movie_max_value);
            listener.onMovieRated(movieRate / numStars * maxValue);
        }
    }

    public interface OnMovieRatedListener {

        void onMovieRated(float value);
    }
}

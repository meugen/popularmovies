package ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.rate;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.RatingBar;

import javax.inject.Inject;
import javax.inject.Named;

import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.databinding.DialogRateMovieBinding;
import ua.meugen.android.popularmovies.ui.activities.base.BaseActivityModule;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.BaseDialogFragment;
import ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.rate.presenter.RateMoviePresenter;
import ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.rate.state.RateMovieState;

/**
 * @author meugen
 */

public class RateMovieDialog extends BaseDialogFragment<RateMovieState, RateMoviePresenter>
        implements RatingBar.OnRatingBarChangeListener, DialogInterface.OnClickListener {

    private static final String PARAM_MOVIE_RATE = "movieRate";

    @Inject @Named(BaseActivityModule.ACTIVITY_CONTEXT)
    Context context;

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final LayoutInflater inflater = LayoutInflater.from(getContext());
        DialogRateMovieBinding binding = DialogRateMovieBinding.inflate(inflater);
        binding.movieRate.setRating(presenter.getMovieRate());
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
        presenter.setMovieRate(rating);
    }

    @Override
    public void onClick(final DialogInterface dialogInterface, final int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            final Resources resources = context.getResources();
            final int numStars = resources.getInteger(R.integer.rate_movie_num_stars);
            final int maxValue = resources.getInteger(R.integer.rate_movie_max_value);
            presenter.movieRated(numStars, maxValue);
        }
    }

}

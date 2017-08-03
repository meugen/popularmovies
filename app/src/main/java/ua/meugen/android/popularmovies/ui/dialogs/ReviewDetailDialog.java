package ua.meugen.android.popularmovies.ui.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.model.responses.ReviewItemDto;

/**
 * @author meugen
 */

public class ReviewDetailDialog extends DialogFragment {

    private static final String PARAM_REVIEW = "review";

    public static ReviewDetailDialog newInstance(final ReviewItemDto dto) {
        final Bundle arguments = new Bundle();
        arguments.putParcelable(PARAM_REVIEW, dto);

        final ReviewDetailDialog dialog = new ReviewDetailDialog();
        dialog.setArguments(arguments);
        return dialog;
    }

    private ReviewItemDto review;

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
        this.review = state.getParcelable(PARAM_REVIEW);
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(PARAM_REVIEW, this.review);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.review_detail_title);
        builder.setMessage(review.getContent());
        builder.setNegativeButton(R.string.button_close, null);
        return builder.create();
    }
}

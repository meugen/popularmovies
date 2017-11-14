package ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.review;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.model.db.entity.ReviewItem;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.BaseDialogFragment;
import ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.review.presenter.ReviewDetailPresenter;
import ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.review.state.ReviewDetailState;
import ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.review.view.ReviewDetailView;

/**
 * @author meugen
 */

public class ReviewDetailDialog extends BaseDialogFragment<ReviewDetailState, ReviewDetailPresenter>
        implements ReviewDetailView {

    public static ReviewDetailDialog newInstance(final String reviewId) {
        final Bundle arguments = new Bundle();
        arguments.putString(ReviewDetailState.PARAM_REVIEW_ID, reviewId);

        final ReviewDetailDialog dialog = new ReviewDetailDialog();
        dialog.setArguments(arguments);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.load();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final ReviewItem review = presenter.getReview();

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.review_detail_title);
        builder.setMessage(review == null ? "" : review.content);
        builder.setNegativeButton(R.string.button_close, null);
        return builder.create();
    }

    @Override
    public void onReviewLoaded(final ReviewItem review) {
        final AlertDialog dialog = (AlertDialog) getDialog();
        if (dialog != null) {
            dialog.setMessage(review.content);
        }
    }
}

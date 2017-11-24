package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.reviews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.databinding.FragmentMovieReviewsBinding;
import ua.meugen.android.popularmovies.model.db.entity.ReviewItem;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.BaseFragment;
import ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.review.ReviewDetailDialog;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.reviews.adapters.OnClickReviewListener;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.reviews.adapters.ReviewsAdapter;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.reviews.presenter.MovieReviewsPresenter;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.reviews.state.MovieReviewsState;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.reviews.view.MovieReviewsView;

/**
 * @author meugen
 */

public class MovieReviewsFragment extends BaseFragment<MovieReviewsState, MovieReviewsPresenter>
        implements MovieReviewsView, OnClickReviewListener {

    private static final String PARAM_MOVIE_ID = "movieId";

    public static MovieReviewsFragment newInstance(final int id) {
        final Bundle arguments = new Bundle();
        arguments.putInt(PARAM_MOVIE_ID, id);

        final MovieReviewsFragment fragment = new MovieReviewsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Inject ReviewsAdapter adapter;

    private FragmentMovieReviewsBinding binding;

    @Nullable
    @Override
    public View onCreateView(
            final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        binding = FragmentMovieReviewsBinding.inflate(
                inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(
            final View view,
            @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.reviews.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.load();
    }

    @Override
    public void onClickReview(final ReviewItem review) {
        final ReviewDetailDialog dialog = ReviewDetailDialog.newInstance(review.id);
        dialog.show(getFragmentManager(), "review_detail");
    }

    @Override
    public void onReviewsLoaded(final List<ReviewItem> reviews) {
        adapter.swapReviews(reviews);
    }
}

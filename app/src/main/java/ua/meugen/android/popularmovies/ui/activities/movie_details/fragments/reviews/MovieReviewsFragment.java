package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.reviews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ua.meugen.android.popularmovies.databinding.FragmentMovieReviewsBinding;
import ua.meugen.android.popularmovies.model.responses.ReviewItemDto;
import ua.meugen.android.popularmovies.presenter.listeners.OnClickReviewListener;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.BaseFragment;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.reviews.presenter.MovieReviewsPresenter;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.reviews.state.MovieReviewsState;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.reviews.view.MovieReviewsView;
import ua.meugen.android.popularmovies.ui.adapters.ReviewsAdapter;
import ua.meugen.android.popularmovies.ui.dialogs.ReviewDetailDialog;

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

    private FragmentMovieReviewsBinding binding;

    private ReviewsAdapter adapter;

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
    public void onClickReview(final ReviewItemDto review) {
        final ReviewDetailDialog dialog = ReviewDetailDialog.newInstance(review);
        dialog.show(getFragmentManager(), "review_detail");
    }

    @Override
    public void onReviewsLoaded(final List<ReviewItemDto> reviews) {
        if (adapter == null) {
            adapter = new ReviewsAdapter(getContext(), this);
            binding.reviews.setAdapter(adapter);
        }
        adapter.setReviews(reviews);
    }
}

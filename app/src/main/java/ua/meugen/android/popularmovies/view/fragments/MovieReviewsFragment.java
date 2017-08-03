package ua.meugen.android.popularmovies.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.meugen.android.popularmovies.PopularMovies;
import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.model.responses.ReviewItemDto;
import ua.meugen.android.popularmovies.presenter.MovieReviewsPresenter;
import ua.meugen.android.popularmovies.presenter.listeners.OnClickReviewListener;
import ua.meugen.android.popularmovies.view.MovieReviewsView;
import ua.meugen.android.popularmovies.view.adapters.ReviewsAdapter;
import ua.meugen.android.popularmovies.view.dialogs.ReviewDetailDialog;

/**
 * @author meugen
 */

public class MovieReviewsFragment extends MvpFragment<MovieReviewsView, MovieReviewsPresenter>
        implements MovieReviewsView, OnClickReviewListener {

    private static final String PARAM_MOVIE_ID = "movieId";

    public static MovieReviewsFragment newInstance(final int id) {
        final Bundle arguments = new Bundle();
        arguments.putInt(PARAM_MOVIE_ID, id);

        final MovieReviewsFragment fragment = new MovieReviewsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @BindView(R.id.reviews) RecyclerView reviewsView;

    private ReviewsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(
            final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_movie_reviews,
                container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.load();
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            restoreInstanceState(getArguments());
        } else {
            restoreInstanceState(savedInstanceState);
        }
    }

    private void restoreInstanceState(final Bundle state) {
        presenter.setMovieId(state.getInt(PARAM_MOVIE_ID));
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PARAM_MOVIE_ID, presenter.getMovieId());
    }

    @Override
    @NonNull
    public MovieReviewsPresenter createPresenter() {
        return PopularMovies.appComponent(getContext())
                .createMovieReviewsPresenter();
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
            reviewsView.setAdapter(adapter);
        }
        adapter.setReviews(reviews);
    }
}

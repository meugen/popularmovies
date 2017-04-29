package ua.meugen.android.popularmovies.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

import ua.meugen.android.popularmovies.adapters.ReviewsAdapter;
import ua.meugen.android.popularmovies.databinding.FragmentMovieReviewsBinding;
import ua.meugen.android.popularmovies.databinding.FragmentMovieVideosBinding;
import ua.meugen.android.popularmovies.dto.PagedReviewsDto;
import ua.meugen.android.popularmovies.loaders.AbstractCallbacks;
import ua.meugen.android.popularmovies.loaders.LoaderResult;
import ua.meugen.android.popularmovies.loaders.MovieReviewsLoader;
import ua.meugen.android.popularmovies.loaders.MovieVideosLoader;

/**
 * @author meugen
 */

public class MovieReviewsFragment extends Fragment {

    private static final String PARAM_ID = "id";
    private static final String PARAM_REVIEWS = "reviews";

    public static MovieReviewsFragment newInstance(final int id) {
        final Bundle arguments = new Bundle();
        arguments.putInt(PARAM_ID, id);

        final MovieReviewsFragment fragment = new MovieReviewsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    private final ReviewsLoaderCallbacks callbacks
            = new ReviewsLoaderCallbacks();

    private int id;
    private PagedReviewsDto reviews;

    private FragmentMovieReviewsBinding binding;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            restoreInstanceState(getArguments());
        } else {
            restoreInstanceState(savedInstanceState);
        }
    }

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
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (reviews == null) {
            loadReviews(false);
        } else {
            setupReviews();
        }
    }

    private void restoreInstanceState(final Bundle state) {
        id = state.getInt(PARAM_ID);
        reviews = state.getParcelable(PARAM_REVIEWS);
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(PARAM_ID, id);
        outState.putParcelable(PARAM_REVIEWS, reviews);
    }

    private void loadReviews(final boolean restart) {
        final LoaderManager loaderManager = getLoaderManager();
        final Bundle params = MovieVideosLoader.buildParams(id);
        if (restart) {
            loaderManager.restartLoader(0, params, callbacks);
        } else {
            loaderManager.initLoader(0, params, callbacks);
        }
    }

    private void setupReviews() {
        binding.reviews.setAdapter(new ReviewsAdapter(getContext(),
                reviews.getResults()));
    }

    private class ReviewsLoaderCallbacks extends AbstractCallbacks<PagedReviewsDto> {

        @Override
        protected void onData(final PagedReviewsDto data) {
            reviews = data;
            setupReviews();
        }

        @Override
        protected void onServerError(final String message, final int code) {}

        @Override
        protected void onNetworkError(final IOException ex) {}

        @Override
        protected void onNoNetwork() {}

        @Override
        public Loader<LoaderResult<PagedReviewsDto>> onCreateLoader(
                final int id, final Bundle args) {
            return new MovieReviewsLoader(getContext(), args);
        }
    }
}

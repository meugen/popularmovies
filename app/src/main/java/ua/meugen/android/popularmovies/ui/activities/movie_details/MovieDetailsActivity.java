package ua.meugen.android.popularmovies.ui.activities.movie_details;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import javax.inject.Inject;
import javax.inject.Named;

import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.databinding.ActivityMovieDetailsBinding;
import ua.meugen.android.popularmovies.ui.activities.authorize.AuthorizeActivity;
import ua.meugen.android.popularmovies.ui.activities.base.BaseActivity;
import ua.meugen.android.popularmovies.ui.activities.base.BaseActivityModule;
import ua.meugen.android.popularmovies.ui.activities.movie_details.adapters.MovieDetailsPagerAdapter;
import ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.rate.OnMovieRatedListener;
import ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.session.OnSessionTypeSelectedListener;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details.MovieDetailsFragment;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.reviews.MovieReviewsFragment;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.videos.MovieVideosFragment;

public class MovieDetailsActivity extends BaseActivity implements
        OnSessionTypeSelectedListener, OnMovieRatedListener {

    private static final String EXTRA_MOVIE_ID = "movieId";

    private static final String PARAM_ACTIVE_TAB = "activeTab";

    private static final String TAG_DETAILS = "details";
    private static final String TAG_VIDEOS = "videos";
    private static final String TAG_REVIEWS = "reviews";

    public static void start(final Context context, final int movieId) {
        final Intent intent = new Intent(context,
                MovieDetailsActivity.class);
        intent.putExtra(EXTRA_MOVIE_ID, movieId);
        context.startActivity(intent);
    }

    @Inject @Named(BaseActivityModule.ACTIVITY_CONTEXT)
    Context context;
    @Inject FragmentManager fragmentManager;

    private ActivityMovieDetailsBinding binding;
    private MovieDetailsPagerAdapter adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);

        int movieId;
        if (savedInstanceState == null) {
            movieId = getIntent().getIntExtra(EXTRA_MOVIE_ID, 0);

        } else {
            movieId = savedInstanceState.getInt(EXTRA_MOVIE_ID);
        }
        adapter = new MovieDetailsPagerAdapter(
                context, fragmentManager, movieId);
        binding.pager.setAdapter(adapter);
    }

    @Override
    public void onUserSessionSelected() {
        final MovieDetailsFragment fragment = (MovieDetailsFragment) adapter
                .getInstantiatedFragment(binding.pager.getCurrentItem());
        fragment.onUserSessionSelected();
    }

    @Override
    public void onGuestSessionSelected() {
        final MovieDetailsFragment fragment = (MovieDetailsFragment) adapter
                .getInstantiatedFragment(binding.pager.getCurrentItem());
        fragment.onGuestSessionSelected();
    }

    @Override
    public void onMovieRated(final float value) {
        final MovieDetailsFragment fragment = (MovieDetailsFragment) adapter
                .getInstantiatedFragment(binding.pager.getCurrentItem());
        fragment.onMovieRated(value);
    }
}

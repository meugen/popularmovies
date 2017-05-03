package ua.meugen.android.popularmovies.activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.app.ListenersCollector;
import ua.meugen.android.popularmovies.databinding.ActivityMovieDetailsBinding;
import ua.meugen.android.popularmovies.dto.MovieItemDto;
import ua.meugen.android.popularmovies.fragments.MovieDetailsFragment;
import ua.meugen.android.popularmovies.fragments.MovieReviewsFragment;
import ua.meugen.android.popularmovies.fragments.MovieVideosFragment;

public class MovieDetailsActivity extends AppCompatActivity
        implements TabLayout.OnTabSelectedListener, ListenersCollector.Container {

    private static final String EXTRA_MOVIE = "movie";

    private static final String PARAM_ACTIVE_TAB = "activeTab";

    private static final String TAG_DETAILS = "details";
    private static final String TAG_VIDEOS = "videos";
    private static final String TAG_REVIEWS = "reviews";

    public static void start(final Context context, final MovieItemDto movie) {
        final Intent intent = new Intent(context,
                MovieDetailsActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);

        context.startActivity(intent);
    }

    private final ListenersCollector listenersCollector
            = new ListenersCollector();

    private ActivityMovieDetailsBinding binding;
    private MovieDetailsFragment detailsFragment;
    private MovieVideosFragment videosFragment;
    private MovieReviewsFragment reviewsFragment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil
                .setContentView(this, R.layout.activity_movie_details);

        if (savedInstanceState == null) {
            final MovieItemDto movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
            detailsFragment = MovieDetailsFragment.newInstance(movie);
            videosFragment = MovieVideosFragment.newInstance(movie.getId());
            reviewsFragment = MovieReviewsFragment.newInstance(movie.getId());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_container, detailsFragment, TAG_DETAILS)
                    .add(R.id.content_container, videosFragment, TAG_VIDEOS)
                    .add(R.id.content_container, reviewsFragment, TAG_REVIEWS)
                    .hide(videosFragment)
                    .hide(reviewsFragment)
                    .commit();
        } else {
            final FragmentManager fragmentManager = getSupportFragmentManager();
            detailsFragment = (MovieDetailsFragment) fragmentManager
                    .findFragmentByTag(TAG_DETAILS);
            videosFragment = (MovieVideosFragment) fragmentManager
                    .findFragmentByTag(TAG_VIDEOS);
            reviewsFragment = (MovieReviewsFragment) fragmentManager
                    .findFragmentByTag(TAG_REVIEWS);
            binding.tabLayout
                    .getTabAt(savedInstanceState.getInt(PARAM_ACTIVE_TAB))
                    .select();
        }

        binding.tabLayout.addOnTabSelectedListener(this);
    }

    private Fragment getFragmentByTabIndex(final int index) {
        if (index == 0) {
            return detailsFragment;
        } else if (index == 1) {
            return videosFragment;
        } else if (index == 2) {
            return reviewsFragment;
        }
        return null;
    }

    @Override
    public void onTabSelected(final TabLayout.Tab tab) {
        getSupportFragmentManager()
                .beginTransaction()
                .show(getFragmentByTabIndex(tab.getPosition()))
                .commit();
    }

    @Override
    public void onTabUnselected(final TabLayout.Tab tab) {
        getSupportFragmentManager()
                .beginTransaction()
                .hide(getFragmentByTabIndex(tab.getPosition()))
                .commit();
    }

    @Override
    public void onTabReselected(final TabLayout.Tab tab) {}

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PARAM_ACTIVE_TAB, binding.tabLayout.getSelectedTabPosition());
    }

    @NonNull
    @Override
    public ListenersCollector getListenersCollector() {
        return listenersCollector;
    }
}

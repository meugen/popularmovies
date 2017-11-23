package ua.meugen.android.popularmovies.ui.activities.movie_details.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details.MovieDetailsFragment;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.reviews.MovieReviewsFragment;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.videos.MovieVideosFragment;

/**
 * Created by meugen on 23.11.17.
 */

public class MovieDetailsPagerAdapter extends FragmentStatePagerAdapter {

    private static final int PAGES_COUNT = 3;
    private static final int POSITION_DETAILS = 0;
    private static final int POSITION_VIDEOS = 1;
    private static final int POSITION_REVIEWS = 2;

    private final Context context;
    private final int movieId;
    private final SparseArray<WeakReference<Fragment>> fragments;

    public MovieDetailsPagerAdapter(
            final Context context,
            final FragmentManager fm,
            final int movieId) {
        super(fm);
        this.context = context;
        this.movieId = movieId;
        this.fragments = new SparseArray<>();
    }

    public int getMovieId() {
        return movieId;
    }

    @Override
    public Fragment getItem(final int position) {
        if (position == POSITION_DETAILS) {
            return MovieDetailsFragment.newInstance(movieId);
        } else if (position == POSITION_VIDEOS) {
            return MovieVideosFragment.newInstance(movieId);
        } else if (position == POSITION_REVIEWS) {
            return MovieReviewsFragment.newInstance(movieId);
        } else {
            throw new IllegalArgumentException("Wrong position.");
        }
    }

    @Override
    public int getCount() {
        return PAGES_COUNT;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        if (position == POSITION_DETAILS) {
            return context.getText(R.string.tab_movie_detail_main_text);
        } else if (position == POSITION_VIDEOS) {
            return context.getText(R.string.tab_movie_detail_videos_text);
        } else if (position == POSITION_REVIEWS) {
            return context.getText(R.string.tab_movie_detail_reviews_text);
        } else {
            throw new IllegalArgumentException("Wrong position.");
        }
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final Fragment fragment = (Fragment) super.instantiateItem(container, position);
        fragments.put(position, new WeakReference<>(fragment));
        return fragment;
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        super.destroyItem(container, position, object);
        fragments.remove(position);
    }

    public Fragment getInstantiatedFragment(final int position) {
        final WeakReference<Fragment> reference = fragments.get(position);
        return reference == null ? null : reference.get();
    }
}

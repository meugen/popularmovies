package ua.meugen.android.popularmovies.ui.activities.movie_details;

import android.support.v7.app.AppCompatActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ua.meugen.android.popularmovies.app.di.PerActivity;
import ua.meugen.android.popularmovies.app.di.PerFragment;
import ua.meugen.android.popularmovies.ui.activities.base.BaseActivityModule;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details.MovieDetailsFragment;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details.MovieDetailsFragmentModule;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.reviews.MovieReviewsFragment;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.reviews.MovieReviewsFragmentModule;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.videos.MovieVideosFragment;
import ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.videos.MovieVideosFragmentModule;

/**
 * @author meugen
 */
@Module(includes = BaseActivityModule.class)
public abstract class MovieDetailsActivityModule {

    @Binds @PerActivity
    public abstract AppCompatActivity bindAppCompatActivity(final MovieDetailsActivity activity);

    @PerFragment
    @ContributesAndroidInjector(modules = MovieDetailsFragmentModule.class)
    public abstract MovieDetailsFragment contributeMovieDetails();

    @PerFragment
    @ContributesAndroidInjector(modules = MovieVideosFragmentModule.class)
    public abstract MovieVideosFragment contributeMovieVideos();

    @PerFragment
    @ContributesAndroidInjector(modules = MovieReviewsFragmentModule.class)
    public abstract MovieReviewsFragment contributeMovieReviews();
}

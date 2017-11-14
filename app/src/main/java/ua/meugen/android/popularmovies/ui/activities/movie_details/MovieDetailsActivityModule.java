package ua.meugen.android.popularmovies.ui.activities.movie_details;

import android.support.v7.app.AppCompatActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ua.meugen.android.popularmovies.app.di.PerActivity;
import ua.meugen.android.popularmovies.app.di.PerDialog;
import ua.meugen.android.popularmovies.app.di.PerFragment;
import ua.meugen.android.popularmovies.ui.activities.base.BaseActivityModule;
import ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.rate.OnMovieRatedListener;
import ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.rate.RateMovieDialog;
import ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.rate.RateMovieDialogModule;
import ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.review.ReviewDetailDialog;
import ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.review.ReviewDetailDialogModule;
import ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.session.OnSessionTypeSelectedListener;
import ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.session.SelectSessionTypeDialog;
import ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.session.SelectSessionTypeDialogModule;
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
    abstract AppCompatActivity bindAppCompatActivity(final MovieDetailsActivity activity);

    @Binds @PerActivity
    abstract OnSessionTypeSelectedListener bindSessionTypeSelectedListener(
            final MovieDetailsActivity activity);

    @Binds @PerActivity
    abstract OnMovieRatedListener bindMovieRatedListener(
            final MovieDetailsActivity activity);

    @PerFragment
    @ContributesAndroidInjector(modules = MovieDetailsFragmentModule.class)
    abstract MovieDetailsFragment contributeMovieDetails();

    @PerFragment
    @ContributesAndroidInjector(modules = MovieVideosFragmentModule.class)
    abstract MovieVideosFragment contributeMovieVideos();

    @PerFragment
    @ContributesAndroidInjector(modules = MovieReviewsFragmentModule.class)
    abstract MovieReviewsFragment contributeMovieReviews();

    @PerDialog
    @ContributesAndroidInjector(modules = SelectSessionTypeDialogModule.class)
    abstract SelectSessionTypeDialog contributeSelectSessionTypeDialog();

    @PerDialog
    @ContributesAndroidInjector(modules = RateMovieDialogModule.class)
    abstract RateMovieDialog contributeRateMovieDialog();

    @PerDialog
    @ContributesAndroidInjector(modules = ReviewDetailDialogModule.class)
    abstract ReviewDetailDialog contributeReviewDetailDialog();
}

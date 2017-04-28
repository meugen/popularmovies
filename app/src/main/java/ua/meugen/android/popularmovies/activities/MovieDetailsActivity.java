package ua.meugen.android.popularmovies.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import java.util.ArrayList;
import java.util.List;

import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.adapters.NumberAdapter;
import ua.meugen.android.popularmovies.adapters.VideosAdapter;
import ua.meugen.android.popularmovies.dto.MovieItemDto;
import ua.meugen.android.popularmovies.dto.ReviewItemDto;
import ua.meugen.android.popularmovies.dto.VideoItemDto;
import ua.meugen.android.popularmovies.images.FileSize;
import ua.meugen.android.popularmovies.images.ImageLoader;
import ua.meugen.android.popularmovies.loaders.AbstractCallbacks;
import ua.meugen.android.popularmovies.loaders.MovieDetailsLoader;

public class MovieDetailsActivity extends AppCompatActivity
        implements TabLayout.OnTabSelectedListener {

    private static final String EXTRA_ITEM = "item";
    private static final String EXTRA_DETAILS = "details";

    public static void start(final Context context, final MovieItemDto item) {
        final Intent intent = new Intent(context,
                MovieDetailsActivity.class);
        intent.putExtra(EXTRA_ITEM, item);

        context.startActivity(intent);
    }

    private ViewAnimator animator;
    private RecyclerView videosView;
    private RecyclerView reviewsView;

    private MovieItemDto item;
    private Bundle details;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        if (savedInstanceState == null) {
            item = getIntent().getParcelableExtra(EXTRA_ITEM);
        } else {
            item = savedInstanceState.getParcelable(EXTRA_ITEM);
            details = savedInstanceState.getBundle(EXTRA_DETAILS);
        }

        setTitle(item.getTitle());
        final ImageView posterView = (ImageView) findViewById(R.id.poster);
        ImageLoader.from(this).load(FileSize.w(500), item.getPosterPath())
                .into(posterView);
        final TextView releaseDateView = (TextView) findViewById(R.id.release_date);
        releaseDateView.setText(DateFormat.getMediumDateFormat(this)
                .format(item.getReleaseDate()));
        final TextView voteAverageView = (TextView) findViewById(R.id.vote_average);
        voteAverageView.setText(getString(R.string.activity_movie_details_vote_average,
                item.getVoteAverage()));
        final TextView overviewView = (TextView) findViewById(R.id.overview);
        overviewView.setText(item.getOverview());

        this.videosView = (RecyclerView) findViewById(R.id.videos);
        this.reviewsView = (RecyclerView) findViewById(R.id.reviews);
        if (this.details == null) {
            final Bundle params = new MovieDetailsLoader.ParamsBuilder()
                    .movieId(item.getId()).includeVideos()
                    .includeReviews().build();
        } else {
            setupDetails();
        }

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addOnTabSelectedListener(this);
        this.animator = (ViewAnimator) findViewById(R.id.animator);
    }

    private void setupDetails() {
        if (details.containsKey(MovieDetailsLoader.RESULT_VIDEOS)) {
            final List<VideoItemDto> videos = details.getParcelableArrayList(
                    MovieDetailsLoader.RESULT_VIDEOS);
            videosView.setAdapter(new VideosAdapter(this, videos));
        }
        if (details.containsKey(MovieDetailsLoader.RESULT_REVIEWS)) {
            final List<ReviewItemDto> reviews = details.getParcelableArrayList(
                    MovieDetailsLoader.RESULT_REVIEWS);
            // TODO Implement display reviews
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_ITEM, item);
        outState.putBundle(EXTRA_DETAILS, details);
    }

    @Override
    public void onTabSelected(final TabLayout.Tab tab) {
        animator.setDisplayedChild(tab.getPosition());
    }

    @Override
    public void onTabUnselected(final TabLayout.Tab tab) {}

    @Override
    public void onTabReselected(final TabLayout.Tab tab) {}

    private class DetailsLoaderCallbacks extends AbstractCallbacks<Bundle>
}

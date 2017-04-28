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
import ua.meugen.android.popularmovies.dto.MovieItemDto;
import ua.meugen.android.popularmovies.images.FileSize;
import ua.meugen.android.popularmovies.images.ImageLoader;

public class MovieDetailsActivity extends AppCompatActivity
        implements TabLayout.OnTabSelectedListener {

    private static final String EXTRA_ITEM = "item";

    public static void start(final Context context, final MovieItemDto item) {
        final Intent intent = new Intent(context,
                MovieDetailsActivity.class);
        intent.putExtra(EXTRA_ITEM, item);

        context.startActivity(intent);
    }

    private ViewAnimator animator;
    private MovieItemDto item;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        if (savedInstanceState == null) {
            item = getIntent().getParcelableExtra(EXTRA_ITEM);
        } else {
            item = savedInstanceState.getParcelable(EXTRA_ITEM);
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
        final RecyclerView recycler1 = (RecyclerView) findViewById(R.id.recycler1);
        recycler1.setAdapter(new NumberAdapter(this, 1));
        final RecyclerView recycler2 = (RecyclerView) findViewById(R.id.recycler2);
        recycler2.setAdapter(new NumberAdapter(this, 2));

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addOnTabSelectedListener(this);
        this.animator = (ViewAnimator) findViewById(R.id.animator);
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_ITEM, item);
    }

    @Override
    public void onTabSelected(final TabLayout.Tab tab) {
        animator.setDisplayedChild(tab.getPosition());
    }

    @Override
    public void onTabUnselected(final TabLayout.Tab tab) {}

    @Override
    public void onTabReselected(final TabLayout.Tab tab) {}
}

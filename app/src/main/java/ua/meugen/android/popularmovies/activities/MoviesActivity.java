package ua.meugen.android.popularmovies.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import ua.meugen.android.popularmovies.PopularMovies;
import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.adapters.MoviesAdapter;
import ua.meugen.android.popularmovies.adapters.OnMovieClickListener;
import ua.meugen.android.popularmovies.dto.MovieItemDto;
import ua.meugen.android.popularmovies.dto.PagedMoviesDto;
import ua.meugen.android.popularmovies.loaders.AbstractCallbacks;
import ua.meugen.android.popularmovies.loaders.LoaderResult;
import ua.meugen.android.popularmovies.loaders.PopularMoviesLoader;
import ua.meugen.android.popularmovies.loaders.TopRatedMoviesLoader;
import ua.meugen.android.popularmovies.providers.MoviesContract;
import ua.meugen.android.popularmovies.services.UpdateService;
import ua.meugen.android.popularmovies.utils.ConnectivityUtils;

public class MoviesActivity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener, OnMovieClickListener {

    private static final int LOADER_POPULAR = 1;
    private static final int LOADER_TOP_RATED = 2;

    private final MoviesCallbacks moviesCallbacks
            = new MoviesCallbacks();

    private RecyclerView recycler;
    private SwipeRefreshLayout swipeRefresh;

    private MoviesAdapter adapter;

    private BroadcastReceiver waitForAnInternetReceiver;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        recycler = (RecyclerView) findViewById(R.id.recycler);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setOnRefreshListener(this);

        setupAdapter(null);
        refresh();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (waitForAnInternetReceiver != null) {
            unregisterReceiver(waitForAnInternetReceiver);
            waitForAnInternetReceiver = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        final int itemId = item.getItemId();
        if (itemId == R.id.popular) {
            PopularMovies.from(this).setSortType(PopularMovies.SortType.POPULAR);
            refresh();
            return true;
        }
        if (itemId == R.id.top_rated) {
            PopularMovies.from(this).setSortType(PopularMovies.SortType.TOP_RATED);
            refresh();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        final PopularMovies.SortType sortType = PopularMovies.from(this).getSortType();
        if (sortType == PopularMovies.SortType.POPULAR) {
            menu.findItem(R.id.popular).setChecked(true);
        } else if (sortType == PopularMovies.SortType.TOP_RATED) {
            menu.findItem(R.id.top_rated).setChecked(true);
        }
        return true;
    }

    @Override
    public void onRefresh() {
        swipeRefresh.setRefreshing(false);
        refresh();
    }

    @Override
    public void onMovieClick(final int movieId) {
        MovieDetailsActivity.start(this, movieId);
    }

    private void refresh() {
        final PopularMovies.SortType sortType = PopularMovies
                .from(this).getSortType();
        if (sortType == PopularMovies.SortType.POPULAR) {
            getSupportLoaderManager().initLoader(LOADER_POPULAR,
                    null, moviesCallbacks);
            UpdateService.startActionPopular(this);
        } else if (sortType == PopularMovies.SortType.TOP_RATED) {
            getSupportLoaderManager().initLoader(LOADER_TOP_RATED,
                    null, moviesCallbacks);
            UpdateService.startActionTopRated(this);
        }
    }

    private void setupAdapter(final Cursor cursor) {
        if (adapter == null) {
            adapter = new MoviesAdapter(this);
            adapter.setOnMovieClickListener(this);
            recycler.setAdapter(adapter);
        }
        adapter.swapCursor(cursor);
    }

    private void onConnected() {
        unregisterReceiver(waitForAnInternetReceiver);
        waitForAnInternetReceiver = null;

        refresh();
    }

    private class MoviesCallbacks implements LoaderManager.LoaderCallbacks<Cursor>, MoviesContract {

        @Override
        public Loader<Cursor> onCreateLoader(
                final int id, final Bundle args) {
            final String[] columns = new String[] { FIELD_ID, FIELD_POSTER_PATH };
            if (id == LOADER_POPULAR) {
                return new CursorLoader(MoviesActivity.this,
                        POPULAR_URI, columns, null, null, null);
            } else if (id == LOADER_TOP_RATED) {
                return new CursorLoader(MoviesActivity.this,
                        TOP_RATED_URI, columns, null, null, null);
            }
            return null;
        }

        @Override
        public void onLoadFinished(final Loader<Cursor> loader, final Cursor data) {
            setupAdapter(data);
        }

        @Override
        public void onLoaderReset(final Loader<Cursor> loader) {}
    }
}

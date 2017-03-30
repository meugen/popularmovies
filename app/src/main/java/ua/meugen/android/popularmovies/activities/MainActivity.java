package ua.meugen.android.popularmovies.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import ua.meugen.android.popularmovies.PopularMovies;
import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.adapters.MoviesAdapter;
import ua.meugen.android.popularmovies.dto.PagedMoviesDto;
import ua.meugen.android.popularmovies.loaders.AbstractCallbacks;
import ua.meugen.android.popularmovies.loaders.LoaderResult;
import ua.meugen.android.popularmovies.loaders.PopularMoviesLoader;
import ua.meugen.android.popularmovies.loaders.TopRatedMoviesLoader;
import ua.meugen.android.popularmovies.utils.ConnectivityUtils;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final int NO_LOADER = 0;
    private static final int LOADER_POPULAR = 1;
    private static final int LOADER_TOP_RATED = 2;

    private static final String PARAM_ACTIVE_LOADER = "activeLoader";
    private static final String PARAM_MOVIES = "movies";

    private final MoviesCallbacks moviesCallbacks
            = new MoviesCallbacks();

    private View progressBarContainer;
    private View messageContainer;
    private TextView messageView;
    private RecyclerView recycler;
    private SwipeRefreshLayout swipeRefresh;

    private int activeLoader = LOADER_POPULAR;
    private PagedMoviesDto movies;

    private MoviesAdapter adapter;

    private BroadcastReceiver waitForAnInternetReceiver;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBarContainer = findViewById(R.id.progress_bar_container);
        messageContainer = findViewById(R.id.message_container);
        messageView = (TextView) findViewById(R.id.message);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setOnRefreshListener(this);

        if (savedInstanceState == null) {
            final PopularMovies.SortType sortType = PopularMovies.from(this)
                    .getSortType();
            if (sortType == PopularMovies.SortType.TOP_RATED) {
                activeLoader = LOADER_TOP_RATED;
            } else {
                activeLoader = LOADER_POPULAR;
            }
        } else {
            activeLoader = savedInstanceState.getInt(PARAM_ACTIVE_LOADER);
            movies = savedInstanceState.getParcelable(PARAM_MOVIES);
        }

        setupAdapter();
        if (activeLoader != NO_LOADER) {
            getSupportLoaderManager().initLoader(activeLoader, null,
                    moviesCallbacks);
        } else if (movies == null) {
            refresh();
        } else {
            progressBarContainer.setVisibility(View.GONE);
            recycler.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PARAM_ACTIVE_LOADER, activeLoader);
        outState.putParcelable(PARAM_MOVIES, movies);
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

    private void refresh() {
        movies = null;
        final PopularMovies.SortType sortType = PopularMovies.from(this)
                .getSortType();
        if (sortType == PopularMovies.SortType.POPULAR) {
            activeLoader = LOADER_POPULAR;
        } else if (sortType == PopularMovies.SortType.TOP_RATED) {
            activeLoader = LOADER_TOP_RATED;
        }

        recycler.setVisibility(View.GONE);
        messageContainer.setVisibility(View.GONE);
        progressBarContainer.setVisibility(View.VISIBLE);
        getSupportLoaderManager().restartLoader(activeLoader,
                null, moviesCallbacks);
    }

    private void setupAdapter() {
        if (movies == null) {
            return;
        }
        if (adapter == null) {
            adapter = new MoviesAdapter(this, movies.getResults());
            recycler.setAdapter(adapter);
        } else {
            adapter.setItems(movies.getResults());
        }
    }

    private void setupWaitForAnInternet() {
        waitForAnInternetReceiver = new WaitForAnInternetReceiver();
        registerReceiver(waitForAnInternetReceiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    private void onConnected() {
        unregisterReceiver(waitForAnInternetReceiver);
        waitForAnInternetReceiver = null;

        refresh();
    }

    private class MoviesCallbacks extends AbstractCallbacks<PagedMoviesDto> {

        @Override
        protected void onData(final PagedMoviesDto data) {
            movies = data;
            setupAdapter();
            progressBarContainer.setVisibility(View.GONE);
            recycler.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onServerError(final String message, final int code) {
            messageView.setText(getString(R.string.server_returned_error,
                    message, code));
            progressBarContainer.setVisibility(View.GONE);
            messageContainer.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onNetworkError(final IOException ex) {
            messageView.setText(R.string.error_while_fetching_data);
            progressBarContainer.setVisibility(View.GONE);
            messageContainer.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onNoNetwork() {
            messageView.setText(R.string.waiting_for_internet_connection_message);
            progressBarContainer.setVisibility(View.GONE);
            messageContainer.setVisibility(View.VISIBLE);

            setupWaitForAnInternet();
        }

        @Override
        public Loader<LoaderResult<PagedMoviesDto>> onCreateLoader(
                final int id, final Bundle args) {
            if (id == LOADER_POPULAR) {
                return new PopularMoviesLoader(MainActivity.this);
            } else if (id == LOADER_TOP_RATED) {
                return new TopRatedMoviesLoader(MainActivity.this);
            }
            return null;
        }

        @Override
        protected void onCompleted() {
            activeLoader = NO_LOADER;
        }
    }

    private class WaitForAnInternetReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {
            if (ConnectivityUtils.isConnected(context)) {
                onConnected();
            }
        }
    }
}

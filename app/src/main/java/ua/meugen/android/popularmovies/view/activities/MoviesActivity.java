package ua.meugen.android.popularmovies.view.activities;

import android.content.BroadcastReceiver;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.PopularMovies;
import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.databinding.ActivityMoviesBinding;
import ua.meugen.android.popularmovies.view.adapters.MoviesAdapter;
import ua.meugen.android.popularmovies.viewmodel.MoviesViewModel;
import ua.meugen.android.popularmovies.viewmodel.listeners.OnMovieClickListener;
import ua.meugen.android.popularmovies.providers.MoviesContract;
import ua.meugen.android.popularmovies.services.UpdateService;

public class MoviesActivity extends AppCompatActivity {

    @Inject MoviesViewModel model;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PopularMovies.appComponent(this).inject(this);

        setupBinding();
        model.refresh();
    }

    private void setupBinding() {
        ActivityMoviesBinding binding = DataBindingUtil
                .setContentView(this, R.layout.activity_movies);
        binding.setModel(model);
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
            model.refreshPopular();
            return true;
        }
        if (itemId == R.id.top_rated) {
            model.refreshTopRated();
            return true;
        }
        if (itemId == R.id.favorites) {
            model.refreshFavorites();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        final int sortType = model.getSortType();
        if (sortType == PopularMovies.SORT_TYPE_POPULAR) {
            menu.findItem(R.id.popular).setChecked(true);
        } else if (sortType == PopularMovies.SORT_TYPE_TOP_RATED) {
            menu.findItem(R.id.top_rated).setChecked(true);
        } else if (sortType == PopularMovies.SORT_TYPE_FAVORITES) {
            menu.findItem(R.id.favorites).setChecked(true);
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.reset();
    }
}

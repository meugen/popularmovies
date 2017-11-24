package ua.meugen.android.popularmovies.ui.activities.movies.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;
import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.databinding.FragmentMoviesBinding;
import ua.meugen.android.popularmovies.model.SortType;
import ua.meugen.android.popularmovies.model.db.entity.MovieItem;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.BaseFragment;
import ua.meugen.android.popularmovies.ui.activities.movie_details.MovieDetailsActivity;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.adapters.MoviesAdapter;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.adapters.OnMovieClickListener;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.presenter.MoviesPresenter;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.state.MoviesState;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.view.MoviesView;


public class MoviesFragment extends BaseFragment<MoviesState, MoviesPresenter>
        implements MoviesView, OnMovieClickListener, SwipeRefreshLayout.OnRefreshListener {

    @Inject MoviesPresenter presenter;
    @Inject MoviesAdapter adapter;

    private FragmentMoviesBinding binding;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Timber.i("" + this + ": onCreate(" + savedInstanceState + ")");
    }

    @Nullable
    @Override
    public View onCreateView(
            final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        binding = FragmentMoviesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recycler.setAdapter(adapter);
        binding.swipeRefresh.setOnRefreshListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.load();
    }



    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        Timber.i("" + this + ": onSaveInstanceState(" + outState + ")");
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        final int itemId = item.getItemId();
        if (itemId == R.id.popular) {
            presenter.refresh(SortType.POPULAR);
            return true;
        }
        if (itemId == R.id.top_rated) {
            presenter.refresh(SortType.TOP_RATED);
            return true;
        }
        if (itemId == R.id.favorites) {
            presenter.refresh(SortType.FAVORITES);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPrepareOptionsMenu(final Menu menu) {
        super.onPrepareOptionsMenu(menu);
        @SortType
        final int sortType = presenter.getSortType();
        if (sortType == SortType.POPULAR) {
            menu.findItem(R.id.popular).setChecked(true);
        } else if (sortType == SortType.TOP_RATED) {
            menu.findItem(R.id.top_rated).setChecked(true);
        } else if (sortType == SortType.FAVORITES) {
            menu.findItem(R.id.favorites).setChecked(true);
        }
    }

    @Override
    public void showRefreshing() {
        binding.swipeRefresh.setRefreshing(true);
    }

    @Override
    public void showMovies(final List<MovieItem> movies) {
        binding.swipeRefresh.setRefreshing(false);
        adapter.swapMovies(movies);
    }

    @Override
    public void onClickMovie(final int movieId) {
        MovieDetailsActivity.start(getContext(), movieId);
    }

    @Override
    public void onRefresh() {
        presenter.clearCache();
        presenter.refresh(presenter.getSortType());
    }
}

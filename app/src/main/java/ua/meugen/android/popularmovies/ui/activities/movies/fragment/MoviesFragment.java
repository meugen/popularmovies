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

import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.app.PopularMovies;
import ua.meugen.android.popularmovies.databinding.FragmentMoviesBinding;
import ua.meugen.android.popularmovies.model.responses.MovieItemDto;
import ua.meugen.android.popularmovies.app.annotations.SortType;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.listeners.OnMovieClickListener;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.BaseFragment;
import ua.meugen.android.popularmovies.ui.activities.movie_details.MovieDetailsActivity;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.presenter.MoviesPresenter;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.state.MoviesState;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.view.MoviesView;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.adapters.MoviesAdapter;


public class MoviesFragment extends BaseFragment<MoviesState, MoviesPresenter>
        implements MoviesView, OnMovieClickListener, SwipeRefreshLayout.OnRefreshListener {

    @Inject MoviesPresenter presenter;

    private FragmentMoviesBinding binding;
    private MoviesAdapter adapter;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.swipeRefresh.setOnRefreshListener(this);
        refresh();
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
            refresh(SortType.POPULAR);
            return true;
        }
        if (itemId == R.id.top_rated) {
            refresh(SortType.TOP_RATED);
            return true;
        }
        if (itemId == R.id.favorites) {
            refresh(SortType.FAVORITES);
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

    private void refresh() {
        @SortType
        final int sortType = presenter.getSortType();
        presenter.refresh(sortType);
    }

    private void refresh(@SortType final int sortType) {
        presenter.refresh(sortType);
    }

    @Override
    public void showRefreshing() {
        binding.swipeRefresh.setRefreshing(true);
    }

    @Override
    public void showMovies(final List<MovieItemDto> movies) {
        binding.swipeRefresh.setRefreshing(false);
        if (adapter == null) {
            adapter = new MoviesAdapter(getContext(), this);
            binding.recycler.setAdapter(adapter);
        }
        adapter.setMovies(movies);
    }

    @Override
    public void onClickMovie(final int movieId) {
        MovieDetailsActivity.start(getContext(), movieId);
    }

    @Override
    public void onRefresh() {
        refresh();
    }
}

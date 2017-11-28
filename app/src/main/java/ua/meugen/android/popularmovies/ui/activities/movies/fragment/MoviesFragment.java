package ua.meugen.android.popularmovies.ui.activities.movies.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;
import javax.inject.Named;

import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.databinding.FragmentMoviesBinding;
import ua.meugen.android.popularmovies.model.SortType;
import ua.meugen.android.popularmovies.model.network.resp.PagedMoviesResponse;
import ua.meugen.android.popularmovies.ui.activities.base.BaseActivityModule;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.BaseFragment;
import ua.meugen.android.popularmovies.ui.activities.movie_details.MovieDetailsActivity;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.adapters.MoviesAdapter;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.adapters.OnMoviesListener;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.presenter.MoviesPresenter;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.state.MoviesState;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.view.MoviesView;


public class MoviesFragment extends BaseFragment<MoviesState, MoviesPresenter>
        implements MoviesView, OnMoviesListener, SwipeRefreshLayout.OnRefreshListener {

    @Inject @Named(BaseActivityModule.ACTIVITY_CONTEXT)
    Context context;
    @Inject MoviesPresenter presenter;
    @Inject MoviesAdapter adapter;

    private FragmentMoviesBinding binding;

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
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        binding.recycler.setAdapter(adapter);
        binding.swipeRefresh.setOnRefreshListener(this);
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
    public void showMovies(final PagedMoviesResponse movies) {
        binding.swipeRefresh.setRefreshing(false);
        adapter.swapMovies(movies);
        if (binding.recycler.getAdapter() == null) {
            binding.recycler.setAdapter(adapter);
        }
    }

    @Override
    public void showNextPage(final PagedMoviesResponse movies) {
        adapter.swapMovies(movies);
    }

    @Override
    public void onClickMovie(final int movieId) {
        MovieDetailsActivity.start(context, movieId);
    }

    @Override
    public void onLoadNextPage(final int page) {
        presenter.loadNextPage(page);
    }

    @Override
    public void onRefresh() {
        presenter.clearCache();
        presenter.refresh(presenter.getSortType());
    }
}

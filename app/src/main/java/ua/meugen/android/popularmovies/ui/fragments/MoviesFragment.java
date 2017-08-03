package ua.meugen.android.popularmovies.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.app.PopularMovies;
import ua.meugen.android.popularmovies.model.responses.MovieItemDto;
import ua.meugen.android.popularmovies.presenter.MoviesPresenter;
import ua.meugen.android.popularmovies.presenter.annotations.SortType;
import ua.meugen.android.popularmovies.presenter.listeners.OnMovieClickListener;
import ua.meugen.android.popularmovies.ui.MoviesView;
import ua.meugen.android.popularmovies.ui.activities.MovieDetailsActivity;
import ua.meugen.android.popularmovies.ui.adapters.MoviesAdapter;


public class MoviesFragment extends MvpFragment<MoviesView, MoviesPresenter>
        implements MoviesView, OnMovieClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.recycler)
    RecyclerView recycler;
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
        final View view = inflater.inflate(R.layout.fragment_movies, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        swipeRefresh.setOnRefreshListener(this);
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
        final int sortType = PopularMovies.from(getContext()).getSortType();
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
        final int sortType = PopularMovies.from(getContext()).getSortType();
        presenter.refresh(sortType);
    }

    private void refresh(@SortType final int sortType) {
        PopularMovies.from(getContext()).setSortType(sortType);
        presenter.refresh(sortType);
    }

    @Override
    @NonNull
    public MoviesPresenter createPresenter() {
        return PopularMovies.appComponent(getContext()).createMoviesPresenter();
    }

    @Override
    public void showRefreshing() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void showMovies(final List<MovieItemDto> movies) {
        swipeRefresh.setRefreshing(false);
        if (adapter == null) {
            adapter = new MoviesAdapter(getContext(), this);
            recycler.setAdapter(adapter);
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

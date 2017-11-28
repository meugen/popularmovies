package ua.meugen.android.popularmovies.ui.activities.movies.fragment.adapters;

import ua.meugen.android.popularmovies.databinding.ItemMovieBinding;

public interface OnMoviesListener {

    void onClickMovie(int movieId);

    void onLoadNextPage(int page);
}

package ua.meugen.android.popularmovies.ui.activities.movies.fragment.view;

import java.util.List;

import ua.meugen.android.popularmovies.model.db.entity.MovieItem;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.view.MvpView;

/**
 * @author meugen
 */

public interface MoviesView extends MvpView {

    void showRefreshing();

    void showNewMovies(List<MovieItem> movies);

    void showNextPage(List<MovieItem> movies);
}

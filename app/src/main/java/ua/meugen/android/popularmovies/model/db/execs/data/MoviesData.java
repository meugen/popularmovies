package ua.meugen.android.popularmovies.model.db.execs.data;

import java.util.List;

import ua.meugen.android.popularmovies.model.db.entity.MovieItem;

/**
 * @author meugen
 */

public class MoviesData {

    public final List<MovieItem> movies;
    public final int status;

    public MoviesData(
            final List<MovieItem> movies,
            final int status) {
        this.movies = movies;
        this.status = status;
    }
}

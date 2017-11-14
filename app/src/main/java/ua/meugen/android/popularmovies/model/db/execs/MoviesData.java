package ua.meugen.android.popularmovies.model.db.execs;

import java.util.List;

import ua.meugen.android.popularmovies.model.db.entity.MovieItem;

/**
 * @author meugen
 */

public class MoviesData {

    private final List<MovieItem> movies;
    private final int status;
    private final boolean needToSave;

    public MoviesData(
            final List<MovieItem> movies,
            final int status,
            final boolean needToSave) {
        this.movies = movies;
        this.status = status;
        this.needToSave = needToSave;
    }

    public List<MovieItem> getMovies() {
        return movies;
    }

    public int getStatus() {
        return status;
    }

    public boolean isNeedToSave() {
        return needToSave;
    }
}

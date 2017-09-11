package ua.meugen.android.popularmovies.app.executors;

import java.util.List;

import ua.meugen.android.popularmovies.model.responses.MovieItemDto;

/**
 * @author meugen
 */

public class MoviesData {

    private final List<MovieItemDto> movies;
    private final int status;
    private final boolean needToSave;

    public MoviesData(
            final List<MovieItemDto> movies,
            final int status,
            final boolean needToSave) {
        this.movies = movies;
        this.status = status;
        this.needToSave = needToSave;
    }

    public List<MovieItemDto> getMovies() {
        return movies;
    }

    public int getStatus() {
        return status;
    }

    public boolean isNeedToSave() {
        return needToSave;
    }
}

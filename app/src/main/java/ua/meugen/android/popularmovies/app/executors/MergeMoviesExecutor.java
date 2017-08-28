package ua.meugen.android.popularmovies.app.executors;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import ua.meugen.android.popularmovies.model.responses.MovieItemDto;

/**
 * @author meugen
 */

public class MergeMoviesExecutor extends AbstractExecutor<MoviesData> {

    @Inject
    public MergeMoviesExecutor() {}

    @Override
    protected void execute(final Realm realm, final MoviesData data) {
        if (!data.isNeedToSave()) {
            throw new IllegalArgumentException("This data no need to save.");
        }
        for (MovieItemDto movie : data.getMovies()) {
            MovieItemDto persistMovie = realm
                    .where(MovieItemDto.class)
                    .equalTo("id", movie.getId())
                    .findFirst();
            if (persistMovie == null) {
                updateMovie(movie, data.getStatus());
                realm.copyToRealm(movie);
            } else {
                persistMovie.setPosterPath(movie.getPosterPath());
                persistMovie.setAdult(movie.isAdult());
                persistMovie.setOverview(movie.getOverview());
                persistMovie.setReleaseDate(movie.getReleaseDate());
                persistMovie.setOriginalTitle(movie.getOriginalTitle());
                persistMovie.setOriginalLanguage(movie.getOriginalLanguage());
                persistMovie.setTitle(movie.getTitle());
                persistMovie.setBackdropPath(movie.getBackdropPath());
                persistMovie.setPopularity(movie.getPopularity());
                persistMovie.setVoteCount(movie.getVoteCount());
                persistMovie.setVideo(movie.isVideo());
                persistMovie.setVoteAverage(movie.getVoteAverage());
                updateMovie(persistMovie, data.getStatus());
            }
        }
    }

    private void updateMovie(final MovieItemDto movie, final String status) {
        if (MovieItemDto.POPULAR.equals(status)) {
            movie.setPopular(true);
        } else if (MovieItemDto.TOP_RATED.equals(status)) {
            movie.setTopRated(true);
        } else if (MovieItemDto.FAVORITES.equals(status)) {
            movie.setFavorites(true);
        } else {
            throw new IllegalArgumentException("Unknown status: " + status);
        }
    }
}

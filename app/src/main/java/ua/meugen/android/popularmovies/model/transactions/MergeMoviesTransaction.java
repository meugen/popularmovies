package ua.meugen.android.popularmovies.model.transactions;

import java.util.List;

import io.realm.Realm;
import ua.meugen.android.popularmovies.model.dto.MovieItemDto;

/**
 * @author meugen
 */

public class MergeMoviesTransaction implements Realm.Transaction {

    private final List<MovieItemDto> movies;

    public MergeMoviesTransaction(final List<MovieItemDto> movies) {
        this.movies = movies;
    }

    @Override
    public void execute(final Realm realm) {
        for (MovieItemDto movie : movies) {
            MovieItemDto persistMovie = realm
                    .where(MovieItemDto.class)
                    .equalTo("id", movie.getId())
                    .findFirst();
            if (persistMovie == null) {
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
            }
        }
    }
}

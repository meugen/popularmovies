package ua.meugen.android.popularmovies.app.executors;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import ua.meugen.android.popularmovies.model.responses.MovieItemDto;

/**
 * @author meugen
 */

public class MergeMoviesExecutor extends AbstractExecutor<List<MovieItemDto>> {

    @Inject
    public MergeMoviesExecutor() {}

    @Override
    protected void execute(final Realm realm, List<MovieItemDto> movies) {
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

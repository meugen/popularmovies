package ua.meugen.android.popularmovies.app.api;

import io.reactivex.Observable;
import ua.meugen.android.popularmovies.model.Session;
import ua.meugen.android.popularmovies.model.responses.BaseDto;
import ua.meugen.android.popularmovies.model.responses.NewGuestSessionDto;
import ua.meugen.android.popularmovies.model.responses.NewSessionDto;
import ua.meugen.android.popularmovies.model.responses.NewTokenDto;
import ua.meugen.android.popularmovies.model.responses.PagedMoviesDto;
import ua.meugen.android.popularmovies.model.responses.PagedReviewsDto;
import ua.meugen.android.popularmovies.model.responses.VideosDto;

/**
 * @author meugen
 */
public interface ModelApi {

    Observable<PagedMoviesDto> getPopularMovies();

    Observable<PagedMoviesDto> getTopRatedMovies();

    Observable<VideosDto> getMovieVideos(int id);

    Observable<PagedReviewsDto> getMovieReviews(int id);

    Observable<NewTokenDto> createNewToken();

    Observable<NewSessionDto> createNewSession(String token);

    Observable<NewGuestSessionDto> createNewGuestSession();

    Observable<BaseDto> rateMovie(Session session, int id, double value);
}

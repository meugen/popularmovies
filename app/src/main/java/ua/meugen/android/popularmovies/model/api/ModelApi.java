package ua.meugen.android.popularmovies.model.api;

import rx.Observable;
import ua.meugen.android.popularmovies.app.Session;
import ua.meugen.android.popularmovies.model.dto.BaseDto;
import ua.meugen.android.popularmovies.model.dto.NewGuestSessionDto;
import ua.meugen.android.popularmovies.model.dto.NewSessionDto;
import ua.meugen.android.popularmovies.model.dto.NewTokenDto;
import ua.meugen.android.popularmovies.model.dto.PagedMoviesDto;
import ua.meugen.android.popularmovies.model.dto.PagedReviewsDto;
import ua.meugen.android.popularmovies.model.dto.VideosDto;

/**
 * @author meugen
 */
public interface ModelApi {

    Observable<PagedMoviesDto> popularMovies();

    Observable<PagedMoviesDto> topRatedMovies();

    Observable<VideosDto> movieVideos(int id);

    Observable<PagedReviewsDto> movieReviews(int id);

    Observable<NewTokenDto> newToken();

    Observable<NewSessionDto> newSession(String token);

    Observable<NewGuestSessionDto> newGuestSession();

    Observable<BaseDto> rateMovie(Session session, int id, double value);
}

package ua.meugen.android.popularmovies.model.api;

import io.reactivex.Observable;
import ua.meugen.android.popularmovies.model.network.resp.BaseResponse;
import ua.meugen.android.popularmovies.model.network.resp.NewGuestSessionResponse;
import ua.meugen.android.popularmovies.model.network.resp.NewSessionResponse;
import ua.meugen.android.popularmovies.model.network.resp.NewTokenResponse;
import ua.meugen.android.popularmovies.model.network.resp.PagedMoviesResponse;
import ua.meugen.android.popularmovies.model.network.resp.PagedReviewsResponse;
import ua.meugen.android.popularmovies.model.network.resp.VideosResponse;
import ua.meugen.android.popularmovies.model.session.Session;

/**
 * @author meugen
 */
public interface ModelApi {

    Observable<PagedMoviesResponse> getPopularMovies();

    Observable<PagedMoviesResponse> getTopRatedMovies();

    Observable<VideosResponse> getMovieVideos(int id);

    Observable<PagedReviewsResponse> getMovieReviews(int id);

    Observable<NewTokenResponse> createNewToken();

    Observable<NewSessionResponse> createNewSession(String token);

    Observable<NewGuestSessionResponse> createNewGuestSession();

    Observable<BaseResponse> rateMovie(Session session, int id, double value);
}

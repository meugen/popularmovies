package ua.meugen.android.popularmovies.app.api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import ua.meugen.android.popularmovies.model.requests.RateMovieRequest;
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

public interface ServerApi {

    @GET("movie/popular")
    Observable<PagedMoviesDto> getPopularMovies(
            @QueryMap Map<String, String> params);

    @GET("movie/top_rated")
    Observable<PagedMoviesDto> getTopRatedMovies(
            @QueryMap Map<String, String> params);

    @GET("movie/{id}/videos")
    Observable<VideosDto> getMovieVideos(
            @Path("id") int id,
            @QueryMap Map<String, String> params);

    @GET("movie/{id}/reviews")
    Observable<PagedReviewsDto> getMovieReviews(
            @Path("id") int id,
            @QueryMap Map<String, String> params);

    @GET("authentication/token/new")
    Observable<NewTokenDto> createNewToken(
            @QueryMap Map<String, String> params);

    @GET("authentication/session/new")
    Observable<NewSessionDto> createNewSession(
            @QueryMap Map<String, String> params);

    @GET("authentication/guest_session/new")
    Observable<NewGuestSessionDto> createNewGuestSession(
            @QueryMap Map<String, String> params);

    @POST("movie/{id}/rating")
    Observable<BaseDto> rateMovie(
            @Path("id") int id,
            @QueryMap Map<String, String> params,
            @Body RateMovieRequest body);

}

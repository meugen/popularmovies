package ua.meugen.android.popularmovies.model.api;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import ua.meugen.android.popularmovies.model.network.req.RateMovieRequest;
import ua.meugen.android.popularmovies.model.network.resp.BaseResponse;
import ua.meugen.android.popularmovies.model.network.resp.NewGuestSessionResponse;
import ua.meugen.android.popularmovies.model.network.resp.NewSessionResponse;
import ua.meugen.android.popularmovies.model.network.resp.NewTokenResponse;
import ua.meugen.android.popularmovies.model.network.resp.PagedMoviesResponse;
import ua.meugen.android.popularmovies.model.network.resp.PagedReviewsResponse;
import ua.meugen.android.popularmovies.model.network.resp.VideosResponse;

/**
 * @author meugen
 */

public interface ServerApi {

    @GET("movie/popular")
    Single<PagedMoviesResponse> getPopularMovies(
            @Query("language") String language,
            @Query("page") int page);

    @GET("movie/top_rated")
    Single<PagedMoviesResponse> getTopRatedMovies(
            @Query("language") String language,
            @Query("page") int page);

    @GET("movie/{id}/videos")
    Single<VideosResponse> getMovieVideos(
            @Path("id") int id,
            @Query("language") String language);

    @GET("movie/{id}/reviews")
    Single<PagedReviewsResponse> getMovieReviews(
            @Path("id") int id,
            @Query("language") String language);

    @GET("authentication/token/new")
    Single<NewTokenResponse> createNewToken();

    @GET("authentication/session/new")
    Single<NewSessionResponse> createNewSession(
            @Query("request_token") String newToken);

    @GET("authentication/guest_session/new")
    Single<NewGuestSessionResponse> createNewGuestSession();

    @POST("movie/{id}/rating")
    Single<BaseResponse> rateMovie(
            @Path("id") int id,
            @QueryMap Map<String, String> params,
            @Body RateMovieRequest body);

}

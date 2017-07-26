package ua.meugen.android.popularmovies.model.api;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * @author meugen
 */

public interface ServerApi {

    @GET("movie/popular")
    Observable<ResponseBody> getPopularMovies(
            @QueryMap Map<String, String> params);

    @GET("movie/top_rated")
    Observable<ResponseBody> getTopRatedMovies(
            @QueryMap Map<String, String> params);

    @GET("movie/{id}/videos")
    Observable<ResponseBody> getMovieVideos(
            @Path("id") int id,
            @QueryMap Map<String, String> params);

    @GET("movie/{id}/reviews")
    Observable<ResponseBody> getMovieReviews(
            @Path("id") int id,
            @QueryMap Map<String, String> params);

    @GET("authentication/token/new")
    Observable<ResponseBody> createNewToken(
            @QueryMap Map<String, String> params);

    @GET("authentication/session/new")
    Observable<ResponseBody> createNewSession(
            @QueryMap Map<String, String> params);

    @GET("authentication/guest_session/new")
    Observable<ResponseBody> createNewGuestSession(
            @QueryMap Map<String, String> params);

    @POST("movie/{id}/rating")
    Observable<ResponseBody> rateMovie(
            @Path("id") int id,
            @QueryMap Map<String, String> params,
            @Body RequestBody body);

}

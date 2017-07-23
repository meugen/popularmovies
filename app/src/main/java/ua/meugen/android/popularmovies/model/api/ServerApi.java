package ua.meugen.android.popularmovies.model.api;

import java.util.Map;

import rx.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * @author meugen
 */

public interface ServerApi {

    @GET("movie/popular")
    Observable<ResponseBody> popularMovies(
            @QueryMap Map<String, String> params);

    @GET("movie/top_rated")
    Observable<ResponseBody> topRatedMovies(
            @QueryMap Map<String, String> params);

    @GET("movie/{id}/videos")
    Observable<ResponseBody> movieVideos(
            @Path("id") int id,
            @QueryMap Map<String, String> params);

    @GET("movie/{id}/reviews")
    Observable<ResponseBody> movieReviews(
            @Path("id") int id,
            @QueryMap Map<String, String> params);

    @GET("authentication/token/new")
    Observable<ResponseBody> newToken(
            @QueryMap Map<String, String> params);

    @GET("authentication/session/new")
    Observable<ResponseBody> newSession(
            @QueryMap Map<String, String> params);

    @GET("authentication/guest_session/new")
    Observable<ResponseBody> newGuestSession(
            @QueryMap Map<String, String> params);

    @POST("movie/{id}/rating")
    Observable<ResponseBody> rateMovie(
            @Path("id") int id,
            @QueryMap Map<String, String> params,
            @Body RequestBody body);

}

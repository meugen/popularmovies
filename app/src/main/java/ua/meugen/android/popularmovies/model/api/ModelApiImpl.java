package ua.meugen.android.popularmovies.model.api;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import ua.meugen.android.popularmovies.BuildConfig;
import ua.meugen.android.popularmovies.model.network.req.RateMovieRequest;
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

public class ModelApiImpl implements ModelApi {

    private final ServerApi serverApi;

    @Inject
    ModelApiImpl(final ServerApi serverApi) {
        this.serverApi = serverApi;
    }

    private Map<String, String> createQueryMap() {
        final Map<String, String> result = new HashMap<>();
        result.put("api_key", BuildConfig.API_KEY);
        return result;
    }

    @Override
    public Observable<PagedMoviesResponse> getPopularMovies() {
        return serverApi.getPopularMovies(createQueryMap())
                .toObservable();
    }

    @Override
    public Observable<PagedMoviesResponse> getTopRatedMovies() {
        return serverApi.getTopRatedMovies(createQueryMap())
                .toObservable();
    }

    @Override
    public Observable<VideosResponse> getMovieVideos(final int id) {
        return serverApi.getMovieVideos(id, createQueryMap())
                .toObservable();
    }

    @Override
    public Observable<PagedReviewsResponse> getMovieReviews(final int id) {
        return serverApi.getMovieReviews(id, createQueryMap())
                .toObservable();
    }

    @Override
    public Observable<NewTokenResponse> createNewToken() {
        return serverApi.createNewToken(createQueryMap())
                .toObservable();
    }

    @Override
    public Observable<NewSessionResponse> createNewSession(final String token) {
        final Map<String, String> params = createQueryMap();
        params.put("request_token", token);
        return serverApi.createNewSession(params)
                .toObservable();
    }

    @Override
    public Observable<NewGuestSessionResponse> createNewGuestSession() {
        return serverApi.createNewGuestSession(createQueryMap())
                .toObservable();
    }

    @Override
    public Observable<BaseResponse> rateMovie(
            final Session session,
            final int id,
            final double value) {
        final RateMovieRequest request
                = new RateMovieRequest();
        request.value = value;

        final Map<String, String> params = createQueryMap();
        session.bindParams(params);
        return serverApi.rateMovie(id, params, request)
                .toObservable();
    }
}

package ua.meugen.android.popularmovies.app.impls;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Func1;
import ua.meugen.android.popularmovies.app.json.JsonWritable;
import ua.meugen.android.popularmovies.model.Session;
import ua.meugen.android.popularmovies.model.requests.RateMovieRequest;
import ua.meugen.android.popularmovies.model.responses.BaseDto;
import ua.meugen.android.popularmovies.model.responses.NewGuestSessionDto;
import ua.meugen.android.popularmovies.model.responses.NewSessionDto;
import ua.meugen.android.popularmovies.model.responses.NewTokenDto;
import ua.meugen.android.popularmovies.model.responses.PagedMoviesDto;
import ua.meugen.android.popularmovies.model.responses.PagedReviewsDto;
import ua.meugen.android.popularmovies.model.responses.VideosDto;
import ua.meugen.android.popularmovies.presenter.api.ModelApi;
import ua.meugen.android.popularmovies.presenter.api.ServerApi;

/**
 * @author meugen
 */

public class ModelApiImpl implements ModelApi {

    private static final String API_KEY = "4d0aefea62c601c18d2e9902f2570a61";

    @Inject ServerApi serverApi;

    @Inject Func1<ResponseBody, PagedMoviesDto> responseMoviesFunc;
    @Inject Func1<ResponseBody, VideosDto> responseVideosFunc;
    @Inject Func1<ResponseBody, PagedReviewsDto> responseReviewsFunc;
    @Inject Func1<ResponseBody, NewTokenDto> responseTokenFunc;
    @Inject Func1<ResponseBody, NewSessionDto> responseSessionFunc;
    @Inject Func1<ResponseBody, NewGuestSessionDto> responseGuestSessionFunc;
    @Inject Func1<ResponseBody, BaseDto> responseBaseFunc;
    @Inject Func1<JsonWritable, RequestBody> writableRequestFunc;

    @Inject
    public ModelApiImpl() {}

    private Map<String, String> createQueryMap() {
        final Map<String, String> result = new HashMap<>();
        result.put("api_key", API_KEY);
        return result;
    }

    @Override
    public Observable<PagedMoviesDto> getPopularMovies() {
        return serverApi.getPopularMovies(createQueryMap())
                .map(responseMoviesFunc);
    }

    @Override
    public Observable<PagedMoviesDto> getTopRatedMovies() {
        return serverApi.getTopRatedMovies(createQueryMap())
                .map(responseMoviesFunc);
    }

    @Override
    public Observable<VideosDto> getMovieVideos(final int id) {
        return serverApi.getMovieVideos(id, createQueryMap())
                .map(responseVideosFunc);
    }

    @Override
    public Observable<PagedReviewsDto> getMovieReviews(final int id) {
        return serverApi.getMovieReviews(id, createQueryMap())
                .map(responseReviewsFunc);
    }

    @Override
    public Observable<NewTokenDto> createNewToken() {
        return serverApi.createNewToken(createQueryMap())
                .map(responseTokenFunc);
    }

    @Override
    public Observable<NewSessionDto> createNewSession(final String token) {
        final Map<String, String> params = createQueryMap();
        params.put("request_token", token);
        return serverApi.createNewSession(params)
                .map(responseSessionFunc);
    }

    @Override
    public Observable<NewGuestSessionDto> createNewGuestSession() {
        return serverApi.createNewGuestSession(createQueryMap())
                .map(responseGuestSessionFunc);
    }

    @Override
    public Observable<BaseDto> rateMovie(
            final Session session,
            final int id,
            final double value) {
        final RateMovieRequest body = new RateMovieRequest();
        body.setValue(value);

        final Map<String, String> params = createQueryMap();
        session.bindParams(params);
        return Observable.just(body)
                .map(writableRequestFunc)
                .flatMap(request -> serverApi.rateMovie(id, params, request))
                .map(responseBaseFunc);
    }
}

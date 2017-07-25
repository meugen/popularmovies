package ua.meugen.android.popularmovies.model.api.impl;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Func1;
import ua.meugen.android.popularmovies.model.RateMovieBody;
import ua.meugen.android.popularmovies.model.Session;
import ua.meugen.android.popularmovies.model.api.ModelApi;
import ua.meugen.android.popularmovies.model.api.ServerApi;
import ua.meugen.android.popularmovies.model.dto.BaseDto;
import ua.meugen.android.popularmovies.model.dto.NewGuestSessionDto;
import ua.meugen.android.popularmovies.model.dto.NewSessionDto;
import ua.meugen.android.popularmovies.model.dto.NewTokenDto;
import ua.meugen.android.popularmovies.model.dto.PagedMoviesDto;
import ua.meugen.android.popularmovies.model.dto.PagedReviewsDto;
import ua.meugen.android.popularmovies.model.dto.VideosDto;
import ua.meugen.android.popularmovies.model.json.JsonWritable;

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
    public Observable<PagedMoviesDto> popularMovies() {
        return serverApi.popularMovies(createQueryMap())
                .map(responseMoviesFunc);
    }

    @Override
    public Observable<PagedMoviesDto> topRatedMovies() {
        return serverApi.topRatedMovies(createQueryMap())
                .map(responseMoviesFunc);
    }

    @Override
    public Observable<VideosDto> movieVideos(final int id) {
        return serverApi.movieVideos(id, createQueryMap())
                .map(responseVideosFunc);
    }

    @Override
    public Observable<PagedReviewsDto> movieReviews(final int id) {
        return serverApi.movieReviews(id, createQueryMap())
                .map(responseReviewsFunc);
    }

    @Override
    public Observable<NewTokenDto> newToken() {
        return serverApi.newToken(createQueryMap())
                .map(responseTokenFunc);
    }

    @Override
    public Observable<NewSessionDto> newSession(final String token) {
        final Map<String, String> params = createQueryMap();
        params.put("request_token", token);
        return serverApi.newSession(params)
                .map(responseSessionFunc);
    }

    @Override
    public Observable<NewGuestSessionDto> newGuestSession() {
        return serverApi.newGuestSession(createQueryMap())
                .map(responseGuestSessionFunc);
    }

    @Override
    public Observable<BaseDto> rateMovie(
            final Session session,
            final int id,
            final double value) {
        final RateMovieBody body = new RateMovieBody();
        body.setValue(value);

        final Map<String, String> params = createQueryMap();
        session.bindParams(params);
        return Observable.just(body)
                .map(writableRequestFunc)
                .flatMap(request -> serverApi.rateMovie(id, params, request))
                .map(responseBaseFunc);
    }
}

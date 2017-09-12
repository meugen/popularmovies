package ua.meugen.android.popularmovies.app.di.impls;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import ua.meugen.android.popularmovies.model.Session;
import ua.meugen.android.popularmovies.model.requests.RateMovieRequest;
import ua.meugen.android.popularmovies.model.responses.BaseDto;
import ua.meugen.android.popularmovies.model.responses.NewGuestSessionDto;
import ua.meugen.android.popularmovies.model.responses.NewSessionDto;
import ua.meugen.android.popularmovies.model.responses.NewTokenDto;
import ua.meugen.android.popularmovies.model.responses.PagedMoviesDto;
import ua.meugen.android.popularmovies.model.responses.PagedReviewsDto;
import ua.meugen.android.popularmovies.model.responses.VideosDto;
import ua.meugen.android.popularmovies.app.api.ModelApi;
import ua.meugen.android.popularmovies.app.api.ServerApi;

/**
 * @author meugen
 */

public class ModelApiImpl implements ModelApi {

    private static final String API_KEY = "4d0aefea62c601c18d2e9902f2570a61";

    private final ServerApi serverApi;

    @Inject
    public ModelApiImpl(final ServerApi serverApi) {
        this.serverApi = serverApi;
    }

    private Map<String, String> createQueryMap() {
        final Map<String, String> result = new HashMap<>();
        result.put("api_key", API_KEY);
        return result;
    }

    @Override
    public Observable<PagedMoviesDto> getPopularMovies() {
        return serverApi.getPopularMovies(createQueryMap());
    }

    @Override
    public Observable<PagedMoviesDto> getTopRatedMovies() {
        return serverApi.getTopRatedMovies(createQueryMap());
    }

    @Override
    public Observable<VideosDto> getMovieVideos(final int id) {
        return serverApi.getMovieVideos(id, createQueryMap());
    }

    @Override
    public Observable<PagedReviewsDto> getMovieReviews(final int id) {
        return serverApi.getMovieReviews(id, createQueryMap());
    }

    @Override
    public Observable<NewTokenDto> createNewToken() {
        return serverApi.createNewToken(createQueryMap());
    }

    @Override
    public Observable<NewSessionDto> createNewSession(final String token) {
        final Map<String, String> params = createQueryMap();
        params.put("request_token", token);
        return serverApi.createNewSession(params);
    }

    @Override
    public Observable<NewGuestSessionDto> createNewGuestSession() {
        return serverApi.createNewGuestSession(createQueryMap());
    }

    @Override
    public Observable<BaseDto> rateMovie(
            final Session session,
            final int id,
            final double value) {
        final RateMovieRequest request
                = new RateMovieRequest();
        request.setValue(value);

        final Map<String, String> params = createQueryMap();
        session.bindParams(params);
        return serverApi.rateMovie(id, params, request);
    }
}

package ua.meugen.android.popularmovies.app;

import android.util.JsonReader;
import android.util.JsonWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import ua.meugen.android.popularmovies.dto.BaseDto;
import ua.meugen.android.popularmovies.dto.NewGuestSessionDto;
import ua.meugen.android.popularmovies.dto.NewSessionDto;
import ua.meugen.android.popularmovies.dto.NewTokenDto;
import ua.meugen.android.popularmovies.dto.PagedMoviesDto;
import ua.meugen.android.popularmovies.dto.PagedReviewsDto;
import ua.meugen.android.popularmovies.dto.VideosDto;
import ua.meugen.android.popularmovies.json.JsonReadable;
import ua.meugen.android.popularmovies.json.JsonWritable;
import ua.meugen.android.popularmovies.utils.IOUtils;

@Singleton
public class Api {

    private static final HttpUrl BASE_URL = HttpUrl.parse("https://api.themoviedb.org/3");
    // TODO insert an api key here
    private static final String API_KEY = "<<API KEY HERE>>";

    private static final String PATH_POPULAR = "movie/popular";
    private static final String PATH_TOP_RATED = "movie/top_rated";
    private static final String PATH_VIDEOS = "movie/%d/videos";
    private static final String PATH_REVIEWS = "movie/%d/reviews";
    private static final String PATH_NEW_TOKEN = "authentication/token/new";
    private static final String PATH_NEW_SESSION = "authentication/session/new";
    private static final String PATH_NEW_GUEST_SESSION = "authentication/guest_session/new";
    private static final String PATH_RATE_MOVIE = "movie/%d/rating";

    @Inject OkHttpClient client;

    @Inject JsonReadable<PagedMoviesDto> pagedMoviesReadable;
    @Inject JsonReadable<VideosDto> videosReadable;
    @Inject JsonReadable<PagedReviewsDto> pagedReviewsReadable;
    @Inject JsonReadable<NewTokenDto> newTokenReadable;
    @Inject JsonReadable<NewSessionDto> newSessionReadable;
    @Inject JsonReadable<NewGuestSessionDto> newGuestSessionReadable;
    @Inject JsonReadable<BaseDto> baseReadable;

    @Inject
    public Api() {}

    private HttpUrl.Builder urlBuilder(final String path) {
        return BASE_URL.newBuilder()
                .addPathSegments(path)
                .addQueryParameter("api_key", API_KEY);
    }

    private <T> T doRequest(
            final Request request,
            final JsonReadable<T> readable) throws IOException {
        Response response = null;
        JsonReader reader = null;
        try {
            response = this.client.newCall(request).execute();
            reader = new JsonReader(response.body().charStream());
            return readable.readJson(reader);
        } finally {
            IOUtils.closeQuietly(response);
            IOUtils.closeQuietly(reader);
        }
    }

    private <T> T execute(
            final HttpUrl url,
            final JsonReadable<T> readable) throws IOException {
        final Request request = new Request.Builder()
                .get().url(url).build();
        return doRequest(request, readable);
    }

    private <T> T execute(
            final HttpUrl url,
            final JsonWritable writable,
            final JsonReadable<T> readable) throws IOException {
        JsonWriter writer = null;
        try {
            final StringWriter stringWriter = new StringWriter();
            writer = new JsonWriter(stringWriter);
            writable.writeToJson(writer);
            writer.flush();

            final MediaType mediaType = MediaType.parse(
                    "application/json; charset=UTF-8");
            final RequestBody body = RequestBody.create(
                    mediaType, stringWriter.toString());
            final Request request = new Request.Builder()
                    .post(body).url(url).build();
            return doRequest(request, readable);
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }

    public PagedMoviesDto popularMovies() throws IOException {
        final HttpUrl url = urlBuilder(PATH_POPULAR)
                .build();
        return execute(url, pagedMoviesReadable);
    }

    public PagedMoviesDto topRatedMovies() throws IOException {
        final HttpUrl url = urlBuilder(PATH_TOP_RATED)
                .build();
        return execute(url, pagedMoviesReadable);
    }

    public VideosDto movieVideos(final int id) throws IOException {
        final HttpUrl url = urlBuilder(String.format(Locale.ENGLISH, PATH_VIDEOS, id))
                .build();
        return execute(url, videosReadable);
    }

    public PagedReviewsDto movieReviews(final int id) throws IOException {
        final HttpUrl url = urlBuilder(String.format(Locale.ENGLISH, PATH_REVIEWS, id))
                .build();
        return execute(url, pagedReviewsReadable);
    }

    public NewTokenDto newToken() throws IOException {
        final HttpUrl url = urlBuilder(PATH_NEW_TOKEN)
                .build();
        return execute(url, newTokenReadable);
    }

    public NewSessionDto newSession(final String token) throws IOException {
        final HttpUrl url = urlBuilder(PATH_NEW_SESSION)
                .addQueryParameter("request_token", token)
                .build();
        return execute(url, newSessionReadable);
    }

    public NewGuestSessionDto newGuestSession() throws IOException {
        final HttpUrl url = urlBuilder(PATH_NEW_GUEST_SESSION)
                .build();
        return execute(url, newGuestSessionReadable);
    }

    public BaseDto rateMovie(
            final Session session,
            final int id,
            final double value) throws IOException {
        final RateMovieBody body = new RateMovieBody();
        body.setValue(value);

        final HttpUrl.Builder builder = urlBuilder(
                String.format(Locale.ENGLISH, PATH_RATE_MOVIE, id));
        session.bindToUrl(builder);
        return execute(builder.build(), body, baseReadable);
    }
}

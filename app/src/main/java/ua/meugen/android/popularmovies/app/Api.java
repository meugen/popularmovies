package ua.meugen.android.popularmovies.app;

import android.util.JsonReader;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ua.meugen.android.popularmovies.dto.PagedMoviesDto;
import ua.meugen.android.popularmovies.json.JsonReadable;
import ua.meugen.android.popularmovies.utils.IOUtils;

public class Api {

    private static final HttpUrl BASE_URL = HttpUrl.parse("https://api.themoviedb.org/3");
    // TODO insert an api key here
    private static final String API_KEY = "<<API KEY HERE>>";

    private static final String PATH_POPULAR = "movie/popular";
    private static final String PATH_TOP_RATED = "movie/top_rated";

    private final OkHttpClient client;

    public Api() {
        this(new OkHttpClient.Builder().build());
    }

    public Api(final OkHttpClient client) {
        this.client = client;
    }

    private HttpUrl.Builder urlBuilder(final String path) {
        return BASE_URL.newBuilder()
                .addPathSegments(path)
                .addQueryParameter("api_key", API_KEY);
    }

    private <T> T execute(final HttpUrl url, final JsonReadable<T> readable) throws IOException {
        final Request request = new Request.Builder()
                .get().url(url).build();

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

    public PagedMoviesDto popularMovies() throws IOException {
        final HttpUrl url = urlBuilder(PATH_POPULAR)
                .build();
        return execute(url, PagedMoviesDto.READABLE);
    }

    public PagedMoviesDto topRatedMovies() throws IOException {
        final HttpUrl url = urlBuilder(PATH_TOP_RATED)
                .build();
        return execute(url, PagedMoviesDto.READABLE);
    }
}

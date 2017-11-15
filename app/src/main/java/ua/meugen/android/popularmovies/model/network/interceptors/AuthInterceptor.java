package ua.meugen.android.popularmovies.model.network.interceptors;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import ua.meugen.android.popularmovies.BuildConfig;

/**
 * Created by meugen on 15.11.2017.
 */
@Singleton
public class AuthInterceptor implements Interceptor {

    @Inject
    public AuthInterceptor() {}

    @Override
    public Response intercept(final Chain chain) throws IOException {
        final Request request = chain.request();
        final HttpUrl newUrl = request.url().newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .build();
        final Request newRequest = request.newBuilder()
                .url(newUrl).build();
        return chain.proceed(newRequest);
    }
}

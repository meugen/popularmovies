package ua.meugen.android.popularmovies.app.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ua.meugen.android.popularmovies.BuildConfig;
import ua.meugen.android.popularmovies.app.PopularMovies;
import ua.meugen.android.popularmovies.app.di.db.DbModule;
import ua.meugen.android.popularmovies.app.di.impls.ModelApiImpl;
import ua.meugen.android.popularmovies.app.di.impls.SessionStorageImpl;
import ua.meugen.android.popularmovies.app.api.ModelApi;
import ua.meugen.android.popularmovies.app.api.ServerApi;
import ua.meugen.android.popularmovies.app.di.ints.SessionStorage;
import ua.meugen.android.popularmovies.ui.activities.authorize.AuthorizeActivity;
import ua.meugen.android.popularmovies.ui.activities.authorize.AuthorizeActivityModule;
import ua.meugen.android.popularmovies.ui.activities.movie_details.MovieDetailsActivity;
import ua.meugen.android.popularmovies.ui.activities.movie_details.MovieDetailsActivityModule;
import ua.meugen.android.popularmovies.ui.activities.movies.MoviesActivity;
import ua.meugen.android.popularmovies.ui.activities.movies.MoviesActivityModule;

/**
 * @author meugen
 */

@Module(includes = { AndroidSupportInjectionModule.class, DbModule.class })
public abstract class AppModule {

    @Binds @Singleton
    public abstract Application bindApplication(final PopularMovies popularMovies);

    @Binds @Singleton
    public abstract Context bindApplicationContext(final Application application);

    @Provides @Singleton
    public static OkHttpClient provideHttpClient() {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }
        return builder.build();
    }

    @Provides @Singleton
    public static ServerApi provideApi(final OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client).baseUrl(BuildConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(ServerApi.class);
    }

    @Binds @Singleton
    public abstract ModelApi bindModelApi(final ModelApiImpl impl);

    @Binds @Singleton
    public abstract SessionStorage bindSessionStorage(final SessionStorageImpl impl);

    @PerActivity
    @ContributesAndroidInjector(modules = MoviesActivityModule.class)
    public abstract MoviesActivity contributeMoviesActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = MovieDetailsActivityModule.class)
    public abstract MovieDetailsActivity contributeMovieDetailsActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = AuthorizeActivityModule.class)
    public abstract AuthorizeActivity contributeAuthorizeActivity();
}

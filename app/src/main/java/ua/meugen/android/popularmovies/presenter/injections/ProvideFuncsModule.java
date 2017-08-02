package ua.meugen.android.popularmovies.presenter.injections;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.ResponseBody;
import rx.functions.Func1;
import ua.meugen.android.popularmovies.model.functions.ResponseToModelFunc;
import ua.meugen.android.popularmovies.model.json.JsonReadable;
import ua.meugen.android.popularmovies.model.responses.BaseDto;
import ua.meugen.android.popularmovies.model.responses.NewGuestSessionDto;
import ua.meugen.android.popularmovies.model.responses.NewSessionDto;
import ua.meugen.android.popularmovies.model.responses.NewTokenDto;
import ua.meugen.android.popularmovies.model.responses.PagedMoviesDto;
import ua.meugen.android.popularmovies.model.responses.PagedReviewsDto;
import ua.meugen.android.popularmovies.model.responses.VideosDto;

/**
 * @author meugen
 */
@Module
public class ProvideFuncsModule {

    @Provides @Singleton
    public Func1<ResponseBody, PagedMoviesDto> provideResponseMoviesFunc(
            final JsonReadable<PagedMoviesDto> readable) {
        return new ResponseToModelFunc<>(readable);
    }

    @Provides @Singleton
    public Func1<ResponseBody, VideosDto> provideResponseVideosFunc(
            final JsonReadable<VideosDto> readable) {
        return new ResponseToModelFunc<>(readable);
    }

    @Provides @Singleton
    public Func1<ResponseBody, PagedReviewsDto> provideResponseReviewsFunc(
            final JsonReadable<PagedReviewsDto> readable) {
        return new ResponseToModelFunc<>(readable);
    }

    @Provides @Singleton
    public Func1<ResponseBody, NewTokenDto> provideResponseTokenFunc(
            final JsonReadable<NewTokenDto> readable) {
        return new ResponseToModelFunc<>(readable);
    }

    @Provides @Singleton
    public Func1<ResponseBody, NewSessionDto> provideResponseSessionFunc(
            final JsonReadable<NewSessionDto> readable) {
        return new ResponseToModelFunc<>(readable);
    }

    @Provides @Singleton
    public Func1<ResponseBody, NewGuestSessionDto> provideResponseGuestSessionFunc(
            final JsonReadable<NewGuestSessionDto> readable) {
        return new ResponseToModelFunc<>(readable);
    }

    @Provides @Singleton
    public Func1<ResponseBody, BaseDto> provideResponseBaseFunc(
            final JsonReadable<BaseDto> readable) {
        return new ResponseToModelFunc<>(readable);
    }
}

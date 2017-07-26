package ua.meugen.android.popularmovies.viewmodel.injections;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import ua.meugen.android.popularmovies.model.responses.BaseDto;
import ua.meugen.android.popularmovies.model.responses.MovieItemDto;
import ua.meugen.android.popularmovies.model.responses.NewGuestSessionDto;
import ua.meugen.android.popularmovies.model.responses.NewSessionDto;
import ua.meugen.android.popularmovies.model.responses.NewTokenDto;
import ua.meugen.android.popularmovies.model.responses.PagedMoviesDto;
import ua.meugen.android.popularmovies.model.responses.PagedReviewsDto;
import ua.meugen.android.popularmovies.model.responses.ReviewItemDto;
import ua.meugen.android.popularmovies.model.responses.VideoItemDto;
import ua.meugen.android.popularmovies.model.responses.VideosDto;
import ua.meugen.android.popularmovies.model.json.JsonReadable;
import ua.meugen.android.popularmovies.model.json.readables.BaseDtoReadable;
import ua.meugen.android.popularmovies.model.json.readables.MovieItemDtoReadable;
import ua.meugen.android.popularmovies.model.json.readables.NewGuestSessionDtoReadable;
import ua.meugen.android.popularmovies.model.json.readables.NewSessionDtoReadable;
import ua.meugen.android.popularmovies.model.json.readables.NewTokenDtoReadable;
import ua.meugen.android.popularmovies.model.json.readables.PagedMoviesDtoReadable;
import ua.meugen.android.popularmovies.model.json.readables.PagedReviewsDtoReadable;
import ua.meugen.android.popularmovies.model.json.readables.ReviewItemDtoReadable;
import ua.meugen.android.popularmovies.model.json.readables.VideoItemDtoReadable;
import ua.meugen.android.popularmovies.model.json.readables.VideosDtoReadable;

@Module
public abstract class ReadablesModule {

    @Binds @Singleton
    public abstract JsonReadable<BaseDto> bindBaseDtoReadable(
            final BaseDtoReadable readable);

    @Binds @Singleton
    public abstract JsonReadable<MovieItemDto> bindMovieItemDtoReadable(
            final MovieItemDtoReadable readable);

    @Binds @Singleton
    public abstract JsonReadable<NewGuestSessionDto> bindNewGuestSessionReadable(
            final NewGuestSessionDtoReadable readable);

    @Binds @Singleton
    public abstract JsonReadable<NewSessionDto> bindNewSessionDtoReadable(
            final NewSessionDtoReadable readable);

    @Binds @Singleton
    public abstract JsonReadable<NewTokenDto> bindNewTokenDtoReadable(
            final NewTokenDtoReadable readable);

    @Binds @Singleton
    public abstract JsonReadable<PagedMoviesDto> bindPagedMoviesDtoReadable(
            final PagedMoviesDtoReadable readable);

    @Binds @Singleton
    public abstract JsonReadable<PagedReviewsDto> bindPagedReviewsDtoReadable(
            final PagedReviewsDtoReadable readable);

    @Binds @Singleton
    public abstract JsonReadable<ReviewItemDto> bindReviewItemDtoReadable(
            final ReviewItemDtoReadable readable);

    @Binds @Singleton
    public abstract JsonReadable<VideoItemDto> bindVideoItemDtoReadable(
            final VideoItemDtoReadable readable);

    @Binds @Singleton
    public abstract JsonReadable<VideosDto> bindVideosDtoReadable(
            final VideosDtoReadable readable);
}

package ua.meugen.android.popularmovies.injections;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import ua.meugen.android.popularmovies.dto.BaseDto;
import ua.meugen.android.popularmovies.dto.MovieItemDto;
import ua.meugen.android.popularmovies.dto.NewGuestSessionDto;
import ua.meugen.android.popularmovies.dto.NewSessionDto;
import ua.meugen.android.popularmovies.dto.NewTokenDto;
import ua.meugen.android.popularmovies.dto.PagedMoviesDto;
import ua.meugen.android.popularmovies.dto.PagedReviewsDto;
import ua.meugen.android.popularmovies.dto.ReviewItemDto;
import ua.meugen.android.popularmovies.dto.VideoItemDto;
import ua.meugen.android.popularmovies.dto.VideosDto;
import ua.meugen.android.popularmovies.json.JsonReadable;
import ua.meugen.android.popularmovies.json.readables.BaseDtoReadable;
import ua.meugen.android.popularmovies.json.readables.MovieItemDtoReadable;
import ua.meugen.android.popularmovies.json.readables.NewGuestSessionDtoReadable;
import ua.meugen.android.popularmovies.json.readables.NewSessionDtoReadable;
import ua.meugen.android.popularmovies.json.readables.NewTokenDtoReadable;
import ua.meugen.android.popularmovies.json.readables.PagedMoviesDtoReadable;
import ua.meugen.android.popularmovies.json.readables.PagedReviewsDtoReadable;
import ua.meugen.android.popularmovies.json.readables.ReviewItemDtoReadable;
import ua.meugen.android.popularmovies.json.readables.VideoItemDtoReadable;
import ua.meugen.android.popularmovies.json.readables.VideosDtoReadable;

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

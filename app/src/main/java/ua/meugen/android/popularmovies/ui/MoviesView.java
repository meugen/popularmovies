package ua.meugen.android.popularmovies.ui;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

import ua.meugen.android.popularmovies.model.responses.MovieItemDto;

public interface MoviesView extends MvpView {

    void showRefreshing();

    void showMovies(List<MovieItemDto> movies);
}

package ua.meugen.android.popularmovies.presenter;

import android.databinding.ObservableField;
import android.view.View;

import ua.meugen.android.popularmovies.model.responses.MovieItemDto;
import ua.meugen.android.popularmovies.presenter.listeners.OnMovieClickListener;

/**
 * @author meugen
 */

public class MovieItemViewModel implements View.OnClickListener {

    public final ObservableField<MovieItemDto> movie;

    private final OnMovieClickListener listener;

    public MovieItemViewModel(final MovieItemDto movie, final OnMovieClickListener listener) {
        this.movie = new ObservableField<>(movie);
        this.listener = listener;
    }

    public void setMovie(final MovieItemDto movie) {
        this.movie.set(movie);
    }

    @Override
    public void onClick(final View view) {
        if (listener != null) {
            listener.onMovieClick(movie.get().getId());
        }
    }
}

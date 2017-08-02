package ua.meugen.android.popularmovies.view;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import ua.meugen.android.popularmovies.model.responses.MovieItemDto;


public interface MovieDetailsView extends MvpView {

    void gotMovie(MovieItemDto movie);

    void rateMovieWithSession();

    void selectSession();

    void sendMessage(CharSequence message);
}

package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.details.view;

import ua.meugen.android.popularmovies.model.db.entity.MovieItem;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.view.MvpView;


public interface MovieDetailsView extends MvpView {

    void gotMovie(MovieItem movie);

    void rateMovieWithSession();

    void selectSession();

    void onMovieRatedSuccess();

    void onServerError(String message);

    void onError();
}

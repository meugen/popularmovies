package ua.meugen.android.popularmovies.app.di.db.movie;

/**
 * @author meugen
 */
public interface MovieContract {

    String TABLE = "movies";

    public interface Fields {

        String ID = "id";
        String POSTER_PATH = "poster_path";
        String ADULT = "adult";
        String OVERVIEW = "overview";
        String RELEASE_DATE = "release_date";
        String ORIGINAL_TITLE = "original_title";
        String ORIGINAL_LANGUAGE = "original_language";
        String TITLE = "title";
        String BACKDROP_PATH = "backdrop_path";
        String POPULARITY = "popularity";
        String VOTE_COUNT = "vote_count";
        String VIDEO = "video";
        String VOTE_AVERAGE = "vote_average";
        String STATUS = "status";
    }
}

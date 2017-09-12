package ua.meugen.android.popularmovies.app.di.db.movie;

/**
 * @author meugen
 */
public interface MovieContract extends BaseContract {

    String TABLE = "movies";

    String FIELD_ID = "id";
    String FIELD_POSTER_PATH = "poster_path";
    String FIELD_ADULT = "adult";
    String FIELD_OVERVIEW = "overview";
    String FIELD_RELEASE_DATE = "release_date";
    String FIELD_ORIGINAL_TITLE = "original_title";
    String FIELD_ORIGINAL_LANGUAGE = "original_language";
    String FIELD_TITLE = "title";
    String FIELD_BACKDROP_PATH = "backdrop_path";
    String FIELD_POPULARITY = "popularity";
    String FIELD_VOTE_COUNT = "vote_count";
    String FIELD_VIDEO = "video";
    String FIELD_VOTE_AVERAGE = "vote_average";
    String FIELD_STATUS = "status";
}

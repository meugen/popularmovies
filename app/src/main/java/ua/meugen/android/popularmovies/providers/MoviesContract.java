package ua.meugen.android.popularmovies.providers;

import android.net.Uri;

/**
 * Created by meugen on 06.05.17.
 */

public interface MoviesContract {

    String POPULAR = "popular";
    String TOP_RATED = "top_rated";
    String FAVORITES = "favorites";
    String MOVIES = "movies";

    String AUTHORITY = "ua.meugen.android.popularmovies";
    Uri BASE_URI = new Uri.Builder()
            .scheme("content")
            .authority(AUTHORITY)
            .build();
    Uri POPULAR_URI = BASE_URI
            .buildUpon()
            .appendPath(POPULAR)
            .build();
    Uri TOP_RATED_URI = BASE_URI
            .buildUpon()
            .appendPath(TOP_RATED)
            .build();
    Uri FAVORITES_URI = BASE_URI
            .buildUpon()
            .appendPath(FAVORITES)
            .build();
    Uri MOVIES_URI = BASE_URI
            .buildUpon()
            .appendPath(MOVIES)
            .build();

    String TABLE_MOVIES = "movies";
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

    String TABLE_MOVIE_TYPES = "movie_types";
    String FIELD_MOVIE_ID = "movie_id";
    String FIELD_TYPE = "type";

    String SELECTION_BY_TYPE = FIELD_ID + " in (SELECT "
            + FIELD_MOVIE_ID + " FROM " + TABLE_MOVIE_TYPES
            + " WHERE " + FIELD_TYPE + "=?)";
    String SELECTION_BY_ID = FIELD_ID + "=?";
}

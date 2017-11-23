package ua.meugen.android.popularmovies.model.prefs;

import ua.meugen.android.popularmovies.model.SortType;

/**
 * Created by meugen on 23.11.2017.
 */

public interface PrefsStorage {

    @SortType
    int getSortType();

    void setSortType(@SortType int value);
}

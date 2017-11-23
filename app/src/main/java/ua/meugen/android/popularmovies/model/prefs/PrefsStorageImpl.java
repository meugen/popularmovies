package ua.meugen.android.popularmovies.model.prefs;

import android.content.SharedPreferences;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.model.SortType;

/**
 * Created by meugen on 23.11.2017.
 */

public class PrefsStorageImpl implements PrefsStorage {

    private static final String PREF_SORT_TYPE = "sortType";

    @Inject SharedPreferences prefs;

    @Inject
    PrefsStorageImpl() {}

    @Override
    public int getSortType() {
        return prefs.getInt(PREF_SORT_TYPE, SortType.POPULAR);
    }

    @Override
    public void setSortType(final int value) {
        prefs.edit()
                .putInt(PREF_SORT_TYPE, value)
                .apply();
    }
}

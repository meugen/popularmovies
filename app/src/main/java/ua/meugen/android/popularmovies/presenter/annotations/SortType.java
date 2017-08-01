package ua.meugen.android.popularmovies.presenter.annotations;

import android.support.annotation.IntDef;

@IntDef({SortType.POPULAR, SortType.TOP_RATED, SortType.FAVORITES})
public @interface SortType {

    int POPULAR = 1;
    int TOP_RATED = 2;
    int FAVORITES = 3;
}

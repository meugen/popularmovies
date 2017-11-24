package ua.meugen.android.popularmovies.model.cache;

import io.reactivex.Maybe;

/**
 * Created by meugen on 24.11.2017.
 */

public interface Cache {

    <T> T get(String key);

    <T> void set(String key, T value);

    void clear();
}

package ua.meugen.android.popularmovies.model.cache;

/**
 * Created by meugen on 24.11.2017.
 */

public interface Cache {

    <T> T get(String key);

    <T> void set(String key, T value);

    void clear(String key);

    void clear();
}

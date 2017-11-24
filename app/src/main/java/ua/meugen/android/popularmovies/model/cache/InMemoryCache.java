package ua.meugen.android.popularmovies.model.cache;

import android.util.LruCache;

import javax.inject.Inject;

import io.reactivex.Maybe;

/**
 * Created by meugen on 24.11.2017.
 */

public class InMemoryCache implements Cache {

    private static final int MAX_SIZE = 10;

    private final LruCache<String, Object> lruCache;

    @Inject
    InMemoryCache() {
        this.lruCache = new LruCache<>(MAX_SIZE);
    }

    @Override
    public <T> T get(final String key) {
        return (T) lruCache.get(key);
    }

    @Override
    public <T> void set(final String key, final T value) {
        lruCache.put(key, value);
    }

    @Override
    public void clear(final String key) {
        lruCache.remove(key);
    }

    @Override
    public void clear() {
        lruCache.evictAll();
    }
}

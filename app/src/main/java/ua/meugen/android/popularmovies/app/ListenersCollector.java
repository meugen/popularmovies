package ua.meugen.android.popularmovies.app;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ListenersCollector {

    @NonNull
    public static ListenersCollector from(final Object object) {
        if (object instanceof ListenersCollector) {
            return (ListenersCollector) object;
        } else if (object instanceof Container) {
            return ((Container) object).getListenersCollector();
        }
        throw new IllegalArgumentException("No listeners collector associated with object: " + object);
    }

    private final Map<UUID, WeakReference<?>> listeners
            = new HashMap<>();

    public <T> UUID registerListener(final UUID key, final T listener) {
        UUID newKey = key;
        while (newKey == null) {
            final UUID genKey = UUID.randomUUID();
            if (!listeners.containsKey(genKey)) {
                newKey = genKey;
            }
        }
        listeners.put(newKey, new WeakReference<>(listener));
        return newKey;
    }

    public void unregisterListener(final UUID key) {
        listeners.remove(key);
    }

    public <T> T retrieveListener(final UUID key) {
        final WeakReference<?> ref = listeners.get(key);
        return ref == null ? null : (T) ref.get();
    }

    public interface Container {

        @NonNull
        ListenersCollector getListenersCollector();
    }
}

package ua.meugen.android.popularmovies.model.cache;

import java.util.Locale;

/**
 * Created by meugen on 27.11.2017.
 */

public class KeyGeneratorImpl<T> implements KeyGenerator<T> {

    private final String template;

    public KeyGeneratorImpl(final String template) {
        this.template = template;
    }

    @Override
    public String generateKey(final T id) {
        return String.format(Locale.ENGLISH, template, id);
    }
}

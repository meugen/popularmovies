package ua.meugen.android.popularmovies.model.json;

import android.util.JsonReader;

import java.io.IOException;

/**
 * Created by meugen on 28.03.17.
 */

public interface JsonReadable<T> {

    T readJson(JsonReader reader) throws IOException;
}

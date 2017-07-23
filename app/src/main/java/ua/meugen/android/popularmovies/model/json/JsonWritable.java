package ua.meugen.android.popularmovies.model.json;

import android.util.JsonWriter;

import java.io.IOException;

/**
 * @author meugen
 */

public interface JsonWritable {

    void writeToJson(JsonWriter writer) throws IOException;
}

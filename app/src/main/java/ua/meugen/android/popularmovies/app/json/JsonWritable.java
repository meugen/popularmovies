package ua.meugen.android.popularmovies.app.json;

import android.util.JsonWriter;

import java.io.IOException;

/**
 * @author meugen
 */

public interface JsonWritable {

    void writeToJson(JsonWriter writer) throws IOException;
}

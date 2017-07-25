package ua.meugen.android.popularmovies.model;

import android.util.JsonWriter;

import java.io.IOException;

import ua.meugen.android.popularmovies.model.json.JsonWritable;

/**
 * @author meugen
 */

public class RateMovieBody implements JsonWritable {

    private double value;

    public double getValue() {
        return value;
    }

    public void setValue(final double value) {
        this.value = value;
    }

    @Override
    public void writeToJson(final JsonWriter writer) throws IOException {
        writer.beginObject();
        writer.name("value").value(value);
        writer.endObject();
    }
}

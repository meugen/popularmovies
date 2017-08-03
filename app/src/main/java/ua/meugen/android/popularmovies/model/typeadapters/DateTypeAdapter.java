package ua.meugen.android.popularmovies.model.typeadapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author meugen
 */

public class DateTypeAdapter extends TypeAdapter<Date> {

    private static final SimpleDateFormat DATE_FORMAT
            = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    @Override
    public void write(final JsonWriter out, final Date value) throws IOException {
        out.value(DATE_FORMAT.format(value));
    }

    @Override
    public Date read(final JsonReader in) throws IOException {
        try {
            Date result;

            final JsonToken token = in.peek();
            if (token == JsonToken.NUMBER) {
                result = new Date(in.nextLong());
            } else if (token == JsonToken.STRING) {
                result = DATE_FORMAT.parse(in.nextString());
            } else {
                throw new IOException("Can't convert value with token " + token + " to date.");
            }
            return result;
        } catch (ParseException e) {
            throw new IOException("Wrong date format", e);
        }
    }
}

package ua.meugen.android.popularmovies.model.network.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author meugen
 */

public class DateTimeTypeAdapter extends TypeAdapter<Date> {

    private static final SimpleDateFormat DATE_TIME_FORMAT
            = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz", Locale.ENGLISH);

    @Override
    public void write(final JsonWriter out, final Date value) throws IOException {
        out.value(DATE_TIME_FORMAT.format(value));
    }

    @Override
    public Date read(final JsonReader in) throws IOException {
        final String dateString = in.nextString();
        try {
            return DATE_TIME_FORMAT.parse(dateString);
        } catch (ParseException e) {
            throw new IOException("Unknown date format: "
                    + dateString, e);
        }
    }
}

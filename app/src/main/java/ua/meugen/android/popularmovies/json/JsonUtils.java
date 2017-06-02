package ua.meugen.android.popularmovies.json;

import android.util.JsonReader;
import android.util.JsonToken;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by meugen on 28.03.17.
 */

public class JsonUtils {

    private static final SimpleDateFormat DATE_FORMAT
            = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    private static final SimpleDateFormat DATE_TIME_FORMAT
            = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz", Locale.ENGLISH);

    public static final JsonReadable<Integer> INTEGER_READABLE = new JsonReadable<Integer>() {
        @Override
        public Integer readJson(final JsonReader reader) throws IOException {
            return reader.nextInt();
        }
    };

    private JsonUtils() {}

    public static Date nextDate(final JsonReader reader) throws IOException {
        try {
            Date result;

            final JsonToken token = reader.peek();
            if (token == JsonToken.NUMBER) {
                result = new Date(reader.nextLong());
            } else if (token == JsonToken.STRING) {
                result = DATE_FORMAT.parse(reader.nextString());
            } else {
                throw new IOException("Can't convert value with token " + token + " to date.");
            }
            return result;
        } catch (ParseException e) {
            throw new IOException("Wrong date format", e);
        }
    }

    public static <T> List<T> nextList(
            final JsonReader reader,
            final JsonReadable<T> readable) throws IOException {
        final List<T> result = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            result.add(readable.readJson(reader));
        }
        reader.endArray();

        return result;
    }

    public static Date nextDateTime(final JsonReader reader) throws IOException {
        final String dateString = reader.nextString();
        try {
            return DATE_TIME_FORMAT.parse(dateString);
        } catch (ParseException e) {
            throw new IOException("Unknown date format: "
                    + dateString, e);
        }
    }
}

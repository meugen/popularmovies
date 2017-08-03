package ua.meugen.android.popularmovies.app.funcs;

import android.util.JsonWriter;

import java.io.IOException;
import java.io.StringWriter;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.exceptions.Exceptions;
import rx.functions.Func1;
import ua.meugen.android.popularmovies.app.json.JsonWritable;
import ua.meugen.android.popularmovies.app.utils.IOUtils;

/**
 * @author meugen
 */
public class ModelToRequestFunc implements Func1<JsonWritable, RequestBody> {

    @Inject
    public ModelToRequestFunc() {}

    @Override
    public RequestBody call(final JsonWritable writable) {
        JsonWriter writer = null;
        try {
            final StringWriter stringWriter = new StringWriter();
            writer = new JsonWriter(stringWriter);
            writable.writeToJson(writer);
            writer.flush();

            final MediaType mediaType = MediaType.parse(
                    "application/json; charset=UTF-8");
            return RequestBody.create(mediaType,
                    stringWriter.toString());
        } catch (IOException e) {
            throw Exceptions.propagate(e);
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }
}

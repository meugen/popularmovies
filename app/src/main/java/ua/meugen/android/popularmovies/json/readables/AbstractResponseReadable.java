package ua.meugen.android.popularmovies.json.readables;

import android.util.JsonReader;

import java.io.IOException;

import ua.meugen.android.popularmovies.dto.BaseResponse;
import ua.meugen.android.popularmovies.json.JsonReadable;


abstract class AbstractResponseReadable<T extends BaseResponse> implements JsonReadable<T> {

    protected final void _readFromJson(
            final JsonReader reader,
            final String name,
            final T response) throws IOException {
        if ("status_message".equals(name)) {
            response.setStatusMessage(reader.nextString());
        } else if ("status_code".equals(name)) {
            response.setStatusCode(reader.nextInt());
        } else if ("success".equals(name)) {
            response.setSuccess(reader.nextBoolean());
        } else {
            reader.skipValue();
        }
    }
}

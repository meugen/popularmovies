package ua.meugen.android.popularmovies.app.funcs;

import android.util.JsonReader;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.exceptions.Exceptions;
import rx.functions.Func1;
import ua.meugen.android.popularmovies.app.json.JsonReadable;

/**
 * @author meugen
 */

public class ResponseToModelFunc<T> implements Func1<ResponseBody, T> {

    private final JsonReadable<T> readable;

    public ResponseToModelFunc(final JsonReadable<T> readable) {
        this.readable = readable;
    }

    @Override
    public T call(final ResponseBody body) {
        try {
            return readable.readJson(new JsonReader(body.charStream()));
        } catch (IOException e) {
            throw Exceptions.propagate(e);
        }
    }
}

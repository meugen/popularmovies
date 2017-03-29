package ua.meugen.android.popularmovies.loaders;

import java.io.IOException;

public class LoaderResult<T> {

    public static <T> LoaderResult<T> withData(final T data) {
        return new LoaderResult<>(data, null);
    }

    public static <T> LoaderResult<T> withError(final IOException ex) {
        return new LoaderResult<>(null, ex);
    }

    private final T data;
    private final IOException ex;

    private LoaderResult(final T data, final IOException ex) {
        this.data = data;
        this.ex = ex;
    }

    public T getData() throws IOException {
        if (this.ex != null) {
            throw this.ex;
        }
        return this.data;
    }
}

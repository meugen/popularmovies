package ua.meugen.android.popularmovies.app.utils;

import java.io.Closeable;
import java.io.IOException;

public class IOUtils {

    private IOUtils() {}

    public static void closeQuietly(final Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {}
    }
}

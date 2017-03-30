package ua.meugen.android.popularmovies.images;

import android.content.Context;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

public class ImageLoader {

    private static final String BASE_URL = "http://image.tmdb.org/t/p/";

    private static ImageLoader instance;

    public static ImageLoader from(final Context context) {
        if (instance == null) {
            synchronized (ImageLoader.class) {
                if (instance == null) {
                    instance = new ImageLoader(Picasso.with(context));
                }
            }
        }
        return instance;
    }

    private final Picasso picasso;

    private ImageLoader(final Picasso picasso) {
        this.picasso = picasso;
    }

    public RequestCreator load(final FileSize size, final String path) {
        return picasso.load(BASE_URL + size.getCode() + path);
    }
}

package ua.meugen.android.popularmovies.viewmodel.bindings;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import ua.meugen.android.popularmovies.viewmodel.images.FileSize;
import ua.meugen.android.popularmovies.viewmodel.images.ImageLoader;

/**
 * @author meugen
 */

public class ImagesBindings {

    private ImagesBindings() {}

    @BindingAdapter("path")
    public static void bindImagePath(
            final ImageView imageView, final String path) {
        ImageLoader.from(imageView.getContext())
                .load(FileSize.w(500), path).into(imageView);
    }
}

package ua.meugen.android.popularmovies.bindings;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.text.format.DateFormat;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.images.FileSize;
import ua.meugen.android.popularmovies.images.ImageLoader;

/**
 * @author meugen
 */

public class CommonBindings {

    private CommonBindings() {}

    @BindingAdapter({"imagePath", "imageWidth"})
    public static void setImagePath(
            final ImageView imageView,
            final String imagePath,
            final int imageWidth) {
        ImageLoader.from(imageView.getContext())
                .load(FileSize.w(imageWidth), imagePath)
                .into(imageView);
    }

    @BindingAdapter("date")
    public static void setDate(
            final TextView textView,
            final long date) {
        final java.text.DateFormat dateFormat = DateFormat
                .getMediumDateFormat(textView.getContext());
        textView.setText(dateFormat.format(new Date(date)));
    }

    @BindingAdapter("vote")
    public static void setVote(
            final TextView textView,
            final double vote) {
        final Context context = textView.getContext();
        textView.setText(context.getString(R.string.activity_movie_details_vote_average, vote));
    }
}

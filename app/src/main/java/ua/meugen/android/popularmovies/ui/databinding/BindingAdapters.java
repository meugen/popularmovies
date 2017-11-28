package ua.meugen.android.popularmovies.ui.databinding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.model.SortType;
import ua.meugen.android.popularmovies.ui.utils.images.FileSize;
import ua.meugen.android.popularmovies.ui.utils.images.ImageLoader;

/**
 * Created by meugen on 12.09.17.
 */

public class BindingAdapters {

    private BindingAdapters() {}

    @BindingAdapter("onClick")
    public static void setOnClickListener(
            final View view, final OnClickListener listener) {
        view.setOnClickListener(v -> listener.onClick());
    }

    @BindingAdapter("path")
    public static void setPath(final ImageView view, final String path) {
        ImageLoader.from(view.getContext())
                .load(FileSize.w(500), path)
                .into(view);
    }

    @BindingAdapter("date")
    public static void setDate(final TextView view, final Date date) {
        if (date == null) {
            view.setText("");
        } else {
            view.setText(DateFormat.getDateInstance()
                    .format(date));
        }
    }

    @BindingAdapter("vote")
    public static void setVote(final TextView view, final double vote) {
        view.setText(view.getContext().getString(R.string.activity_movie_details_vote_average,
                vote));
    }

    @BindingAdapter("favorite")
    public static void setFavorite(final SwitchCompat view, final int status) {
        view.setChecked((status & SortType.FAVORITES) == SortType.FAVORITES);
    }

    public interface OnClickListener {

        void onClick();
    }
}

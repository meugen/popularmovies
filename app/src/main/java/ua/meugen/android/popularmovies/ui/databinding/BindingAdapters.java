package ua.meugen.android.popularmovies.ui.databinding;

import android.databinding.BindingAdapter;
import android.view.View;

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

    public interface OnClickListener {

        void onClick();
    }
}

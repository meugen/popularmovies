package ua.meugen.android.popularmovies.viewmodel.bindings;

import android.databinding.BindingAdapter;
import android.view.View;

import ua.meugen.android.popularmovies.viewmodel.listeners.OnClickListener;

/**
 * @author meugen
 */

public class ListenersBindings {

    private ListenersBindings() {}

    @BindingAdapter("onClick")
    public static void bindOnClickListener(final View view, final OnClickListener listener) {
        view.setOnClickListener(v -> listener.onClick());
    }
}

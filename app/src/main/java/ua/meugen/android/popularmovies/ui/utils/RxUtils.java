package ua.meugen.android.popularmovies.ui.utils;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by meugen on 17.11.2017.
 */

public class RxUtils {

    private RxUtils() {}

    public static <T> ObservableTransformer<T, T> async() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

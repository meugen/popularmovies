package ua.meugen.android.popularmovies.ui.rxloader;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;

/**
 * Created by meugen on 17.11.2017.
 */

public interface LifecycleHandler {

    <T> ObservableTransformer<T, T> load(int id);

    <T> ObservableTransformer<T, T> reload(int id);

    <T> Observable<T> next(int id);

    boolean hasLoader(int id);

    void clear(int id);
}

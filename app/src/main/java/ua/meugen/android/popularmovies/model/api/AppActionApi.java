package ua.meugen.android.popularmovies.model.api;

import io.reactivex.Observable;

/**
 * Created by meugen on 15.11.2017.
 */

public interface AppActionApi<Req, Resp> {

    Observable<Resp> action(Req req);
}

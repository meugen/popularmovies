package ua.meugen.android.popularmovies.model.api;

/**
 * Created by meugen on 24.11.2017.
 */

public interface AppCachedActionApi<Req, Resp> extends AppActionApi<Req, Resp> {

    void clearCache(Req req);
}

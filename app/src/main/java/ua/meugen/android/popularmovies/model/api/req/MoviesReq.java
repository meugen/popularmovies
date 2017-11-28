package ua.meugen.android.popularmovies.model.api.req;

/**
 * Created by meugen on 28.11.2017.
 */

public class MoviesReq {

    public final int status;
    public final int page;

    public MoviesReq(final int status, final int page) {
        this.status = status;
        this.page = page;
    }
}

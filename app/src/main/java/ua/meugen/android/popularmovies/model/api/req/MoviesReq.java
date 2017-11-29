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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final MoviesReq moviesReq = (MoviesReq) o;
        return status == moviesReq.status && page == moviesReq.page;
    }

    @Override
    public int hashCode() {
        int result = status;
        result = 31 * result + page;
        return result;
    }
}

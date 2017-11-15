package ua.meugen.android.popularmovies.model.api.actions;

import android.support.annotation.NonNull;

import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.javatuples.Unit;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import ua.meugen.android.popularmovies.model.api.AppActionApi;
import ua.meugen.android.popularmovies.model.api.ServerApi;
import ua.meugen.android.popularmovies.model.network.req.RateMovieRequest;
import ua.meugen.android.popularmovies.model.network.resp.BaseResponse;
import ua.meugen.android.popularmovies.model.session.Session;
import ua.meugen.android.popularmovies.model.session.SessionStorage;

/**
 * Created by meugen on 15.11.2017.
 */

public class RateMovieActionApi implements AppActionApi<Pair<Integer, Float>, BaseResponse> {

    @Inject SessionStorage sessionStorage;
    @Inject ServerApi serverApi;

    @Inject
    RateMovieActionApi() {}

    @Override
    public Observable<BaseResponse> action(final Pair<Integer, Float> pair) {
        return Single.zip(Single.just(pair), Single.fromCallable(this::retrieveSession), Pair::add)
                .flatMap(this::networkRequest).toObservable();
    }

    @NonNull
    private Session retrieveSession() {
        final Session session = sessionStorage.getSession();
        if (session == null) {
            throw new UnsupportedOperationException("No active session");
        }
        return session;
    }

    private Single<BaseResponse> networkRequest(final Triplet<Integer, Float, Session> triplet) {
        final RateMovieRequest request
                = new RateMovieRequest();
        request.value = triplet.getValue1();

        final Map<String, String> params = new HashMap<>();
        triplet.getValue2().bindParams(params);
        return serverApi.rateMovie(triplet.getValue0(), params, request);
    }
}

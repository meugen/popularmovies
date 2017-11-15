package ua.meugen.android.popularmovies.model.api.actions;

import javax.inject.Inject;

import io.reactivex.Observable;
import ua.meugen.android.popularmovies.model.api.AppActionApi;
import ua.meugen.android.popularmovies.model.api.ServerApi;
import ua.meugen.android.popularmovies.model.network.resp.NewTokenResponse;

/**
 * Created by meugen on 15.11.2017.
 */

public class NewTokenActionApi implements AppActionApi<Void, NewTokenResponse> {

    @Inject ServerApi serverApi;

    @Inject
    NewTokenActionApi() {}

    @Override
    public Observable<NewTokenResponse> action(final Void aVoid) {
        return serverApi.createNewToken().toObservable();
    }
}

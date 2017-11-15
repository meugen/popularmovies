package ua.meugen.android.popularmovies.model.api.actions;

import javax.inject.Inject;

import io.reactivex.Observable;
import ua.meugen.android.popularmovies.model.api.AppActionApi;
import ua.meugen.android.popularmovies.model.api.ServerApi;
import ua.meugen.android.popularmovies.model.network.resp.NewSessionResponse;

/**
 * Created by meugen on 15.11.2017.
 */

public class NewSessionActionApi implements AppActionApi<String, NewSessionResponse> {

    @Inject ServerApi serverApi;

    @Inject
    NewSessionActionApi() {}

    @Override
    public Observable<NewSessionResponse> action(final String token) {
        return serverApi.createNewSession(token).toObservable();
    }
}

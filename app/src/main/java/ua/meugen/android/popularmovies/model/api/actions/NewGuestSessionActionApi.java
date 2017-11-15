package ua.meugen.android.popularmovies.model.api.actions;

import javax.inject.Inject;

import io.reactivex.Observable;
import ua.meugen.android.popularmovies.model.api.AppActionApi;
import ua.meugen.android.popularmovies.model.api.ServerApi;
import ua.meugen.android.popularmovies.model.network.resp.NewGuestSessionResponse;

/**
 * Created by meugen on 15.11.2017.
 */

public class NewGuestSessionActionApi implements AppActionApi<Void, NewGuestSessionResponse> {

    @Inject ServerApi serverApi;

    @Inject
    NewGuestSessionActionApi() {}

    @Override
    public Observable<NewGuestSessionResponse> action(final Void aVoid) {
        return serverApi.createNewGuestSession().toObservable();
    }
}

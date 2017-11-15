package ua.meugen.android.popularmovies.ui.activities.authorize.fragment;

import android.support.v4.app.Fragment;

import dagger.Binds;
import dagger.Module;
import ua.meugen.android.popularmovies.app.di.PerFragment;
import ua.meugen.android.popularmovies.model.api.AppActionApi;
import ua.meugen.android.popularmovies.model.api.actions.NewSessionActionApi;
import ua.meugen.android.popularmovies.model.api.actions.NewTokenActionApi;
import ua.meugen.android.popularmovies.model.network.resp.NewSessionResponse;
import ua.meugen.android.popularmovies.model.network.resp.NewTokenResponse;
import ua.meugen.android.popularmovies.ui.activities.authorize.fragment.presenter.AuthorizePresenter;
import ua.meugen.android.popularmovies.ui.activities.authorize.fragment.presenter.AuthorizePresenterImpl;
import ua.meugen.android.popularmovies.ui.activities.authorize.fragment.state.AuthorizeState;
import ua.meugen.android.popularmovies.ui.activities.authorize.fragment.state.AuthorizeStateImpl;
import ua.meugen.android.popularmovies.ui.activities.authorize.fragment.view.AuthorizeView;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.BaseFragmentModule;

/**
 * Created by meugen on 11.09.17.
 */
@Module(includes = BaseFragmentModule.class)
public abstract class AuthorizeFragmentModule {

    @Binds @PerFragment
    abstract Fragment bindFragment(final AuthorizeFragment fragment);

    @Binds @PerFragment
    abstract AuthorizePresenter bindPresenter(final AuthorizePresenterImpl impl);

    @Binds @PerFragment
    abstract AuthorizeState bindState(final AuthorizeStateImpl impl);

    @Binds @PerFragment
    abstract AuthorizeView bindView(final AuthorizeFragment fragment);

    @Binds @PerFragment
    abstract AppActionApi<Void, NewTokenResponse> bindNewTokenActionApi(final NewTokenActionApi api);

    @Binds @PerFragment
    abstract AppActionApi<String, NewSessionResponse> bindNewSessionActionApi(final NewSessionActionApi api);
}

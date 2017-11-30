package ua.meugen.android.popularmovies.app.services.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Completable;
import timber.log.Timber;
import ua.meugen.android.popularmovies.app.di.PerService;
import ua.meugen.android.popularmovies.app.services.BaseServiceModule;
import ua.meugen.android.popularmovies.model.SortType;
import ua.meugen.android.popularmovies.model.api.AppActionApi;
import ua.meugen.android.popularmovies.model.api.AppCachedActionApi;
import ua.meugen.android.popularmovies.model.api.req.MoviesReq;
import ua.meugen.android.popularmovies.model.db.entity.MovieItem;

/**
 * Created by meugen on 30.11.2017.
 */

@PerService
public class SyncAdapter extends AbstractThreadedSyncAdapter {

    @Inject AppCachedActionApi<MoviesReq, List<MovieItem>> moviesActionApi;

    @Inject
    SyncAdapter(@Named(BaseServiceModule.SERVICE_CONTEXT) final Context context) {
        this(context, true);
    }

    private SyncAdapter(
            final Context context, final boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(
            final Account account,
            final Bundle extras,
            final String authority,
            final ContentProviderClient provider,
            final SyncResult syncResult) {
        Timber.d("onPerformSync()");
        moviesActionApi.clearCache(new MoviesReq(SortType.POPULAR, 1));
        moviesActionApi.clearCache(new MoviesReq(SortType.TOP_RATED, 1));
        Completable popularCompletable = moviesActionApi
                .action(new MoviesReq(SortType.POPULAR, 1))
                .ignoreElements();
        Completable topRatedCompletable = moviesActionApi
                .action(new MoviesReq(SortType.TOP_RATED, 1))
                .ignoreElements();
        popularCompletable.andThen(topRatedCompletable).subscribe();
    }
}

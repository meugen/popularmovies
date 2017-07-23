package ua.meugen.android.popularmovies.loaders;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.io.IOException;

import ua.meugen.android.popularmovies.model.dto.BaseResponse;


public abstract class AbstractCallbacks<T extends BaseResponse>
        implements LoaderManager.LoaderCallbacks<LoaderResult<T>> {

    private final Handler handler;

    protected AbstractCallbacks() {
        this.handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public final void onLoadFinished(
            final Loader<LoaderResult<T>> loader,
            final LoaderResult<T> data) {
        this.handler.post(new Runnable() {
            @Override
            public void run() {
                _onLoadFinished(data);
            }
        });
    }

    private void _onLoadFinished(final LoaderResult<T> data) {
        try {
            T response = data.getData();
            if (response.isSuccess()) {
                onData(data.getData());
            } else {
                onServerError(response.getStatusMessage(), response.getStatusCode());
            }
        } catch (AbstractLoader.NoNetworkException ex) {
            onNoNetwork();
        } catch (IOException ex) {
            onNetworkError(ex);
        } finally {
            onCompleted();
        }
    }

    @Override
    public void onLoaderReset(final Loader<LoaderResult<T>> loader) {}

    protected abstract void onData(final T data);

    protected abstract void onServerError(final String message, final int code);

    protected abstract void onNetworkError(final IOException ex);

    protected abstract void onNoNetwork();

    protected void onCompleted() {}
}

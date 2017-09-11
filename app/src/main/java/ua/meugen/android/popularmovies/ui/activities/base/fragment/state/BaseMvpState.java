package ua.meugen.android.popularmovies.ui.activities.base.fragment.state;

import android.os.Bundle;

/**
 * @author meugen
 */

public abstract class BaseMvpState implements MvpViewState {

    protected Bundle bundle;

    @Override
    public void attachBundle(final Bundle bundle) {
        this.bundle = bundle == null
                ? Bundle.EMPTY : bundle;
    }

    @Override
    public void detachBundle() {
        this.bundle = null;
    }
}

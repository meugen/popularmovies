package ua.meugen.android.popularmovies.ui.activities.base.fragment.state;

import android.os.Bundle;

/**
 * @author meugen
 */

public interface MvpViewState extends MvpState {

    void attachBundle(final Bundle bundle);

    void detachBundle();
}

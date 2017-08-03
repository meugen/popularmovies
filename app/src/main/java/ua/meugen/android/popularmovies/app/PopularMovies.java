package ua.meugen.android.popularmovies.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import io.realm.Realm;
import timber.log.Timber;
import ua.meugen.android.popularmovies.BuildConfig;
import ua.meugen.android.popularmovies.app.di.AppComponent;
import ua.meugen.android.popularmovies.app.di.AppModule;
import ua.meugen.android.popularmovies.app.di.DaggerAppComponent;
import ua.meugen.android.popularmovies.presenter.annotations.SortType;


public class PopularMovies extends Application {

    private static final String PREF_SORT_TYPE_INT = "sortTypeInt";

    private AppComponent appComponent;

    public static PopularMovies from(final Context context) {
        return (PopularMovies) context.getApplicationContext();
    }

    public static AppComponent appComponent(final Context context) {
        return from(context).getAppComponent();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    private SharedPreferences getPrefs() {
        return PreferenceManager.getDefaultSharedPreferences(this);
    }

    @SortType
    @SuppressWarnings("ResourceType")
    public int getSortType() {
        return getPrefs().getInt(PREF_SORT_TYPE_INT, SortType.POPULAR);
    }

    public void setSortType(@SortType final int sortType) {
        getPrefs().edit()
                .putInt(PREF_SORT_TYPE_INT, sortType)
                .apply();
    }
}

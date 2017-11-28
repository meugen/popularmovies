package ua.meugen.android.popularmovies.model.config;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.os.ConfigurationCompat;
import android.support.v4.os.LocaleListCompat;

import javax.inject.Inject;
import javax.inject.Named;

import ua.meugen.android.popularmovies.app.di.modules.AppModule;

/**
 * Created by meugen on 28.11.2017.
 */

public class ConfigImpl implements Config {

    private final Context context;

    @Inject
    public ConfigImpl(@Named(AppModule.APP_CONTEXT) final Context context) {
        this.context = context;
    }

    @Override
    public String getLanguage() {
        final Configuration configuration = context.getResources().getConfiguration();
        final LocaleListCompat locales = ConfigurationCompat.getLocales(configuration);
        return locales.isEmpty() ? null : locales.get(0).getLanguage();
    }
}

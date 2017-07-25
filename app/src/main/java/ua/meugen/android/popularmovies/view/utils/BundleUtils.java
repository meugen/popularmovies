package ua.meugen.android.popularmovies.view.utils;

import android.os.Bundle;
import android.os.ParcelUuid;

import java.util.UUID;

public class BundleUtils {

    private BundleUtils() {}

    public static UUID getUUID(final Bundle bundle, final String key) {
        final ParcelUuid parcelUuid = bundle.getParcelable(key);
        return parcelUuid == null ? null : parcelUuid.getUuid();
    }

    public static void putUUID(final Bundle bundle, final String key, final UUID uuid) {
        bundle.putParcelable(key, new ParcelUuid(uuid));
    }
}

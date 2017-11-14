package ua.meugen.android.popularmovies.model.db;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by meugen on 13.11.2017.
 */

public class Converters {

    @TypeConverter
    public static Date millisToDate(final long value) {
        return new Date(value);
    }

    @TypeConverter
    public static long dateToMillis(final Date value) {
        return value.getTime();
    }
}

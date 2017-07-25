package ua.meugen.android.popularmovies.view.utils;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author meugen
 */

public class ParcelUtils {

    private static final byte NULL = 0;
    private static final byte NOT_NULL = 1;

    private ParcelUtils() {}

    private static void writeIsNull(final Parcel parcel, final Object value) {
        parcel.writeByte(value == null ? NULL : NOT_NULL);
    }

    private static boolean readIsNull(final Parcel parcel) {
        return parcel.readByte() == NULL;
    }

    public static void writeDate(final Parcel parcel, final Date value) {
        writeIsNull(parcel, value);
        if (value != null) {
            parcel.writeLong(value.getTime());
        }
    }

    public static Date readDate(final Parcel parcel) {
        if (readIsNull(parcel)) {
            return null;
        }
        return new Date(parcel.readLong());
    }

    public static void writeIntegerList(final Parcel parcel, final List<Integer> list) {
        writeIsNull(parcel, list);
        if (list != null) {
            parcel.writeInt(list.size());
            for (Integer item : list) {
                parcel.writeInt(item);
            }
        }
    }

    public static List<Integer> readerIntegerList(final Parcel parcel) {
        if (readIsNull(parcel)) {
            return null;
        }
        final int count = parcel.readInt();
        final List<Integer> result = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            result.add(parcel.readInt());
        }
        return result;
    }
}

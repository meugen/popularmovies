package ua.meugen.android.popularmovies.presenter.images;

import android.util.SparseArray;

public class FileSize {

    public static final FileSize ORIGINAL = new FileSize("original");

    private static final SparseArray<FileSize> SIZES
            = new SparseArray<>();

    public static FileSize w(final int width) {
        FileSize size = SIZES.get(width);
        if (size == null) {
            size = new FileSize("w" + width);
            SIZES.put(width, size);
        }
        return size;
    }

    private final String code;

    private FileSize(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

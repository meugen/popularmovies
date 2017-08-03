package ua.meugen.android.popularmovies.presenter.images;

public class FileSize {

    public static FileSize w(final int width) {
        return new FileSize("w" + width);
    }

    private final String code;

    private FileSize(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

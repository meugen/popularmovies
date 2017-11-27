package ua.meugen.android.popularmovies.model.cache;

/**
 * Created by meugen on 27.11.2017.
 */

public interface KeyGenerator<T> {

    static KeyGenerator<Integer> forMovies() {
        return new KeyGeneratorImpl<>("movies-by-status-%d");
    }

    static KeyGenerator<Integer> forMovieVideos() {
        return new KeyGeneratorImpl<>("movie-%d-videos");
    }

    static KeyGenerator<Integer> forMovieReviews() {
        return new KeyGeneratorImpl<>("movie-%d-reviews");
    }

    String generateKey(T id);
}

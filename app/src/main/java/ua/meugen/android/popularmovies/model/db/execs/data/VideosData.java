package ua.meugen.android.popularmovies.model.db.execs.data;

import java.util.List;

import ua.meugen.android.popularmovies.model.db.entity.VideoItem;

/**
 * Created by meugen on 15.11.2017.
 */

public class VideosData {

    public final List<VideoItem> videos;
    public final int movieId;

    public VideosData(final List<VideoItem> videos, final int movieId) {
        this.videos = videos;
        this.movieId = movieId;
    }
}

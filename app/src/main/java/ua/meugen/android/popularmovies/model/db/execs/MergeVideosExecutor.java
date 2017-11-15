package ua.meugen.android.popularmovies.model.db.execs;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.model.db.dao.VideosDao;
import ua.meugen.android.popularmovies.model.db.entity.VideoItem;
import ua.meugen.android.popularmovies.model.db.execs.data.VideosData;

/**
 * Created by meugen on 15.11.2017.
 */

public class MergeVideosExecutor extends AbstractExecutor<VideosData> {

    @Inject VideosDao videosDao;

    @Inject
    MergeVideosExecutor() {}

    @Override
    protected void execute(final VideosData data) {
        for (VideoItem video : data.videos) {
            video.movieId = data.movieId;
        }
        videosDao.merge(data.videos);
    }
}

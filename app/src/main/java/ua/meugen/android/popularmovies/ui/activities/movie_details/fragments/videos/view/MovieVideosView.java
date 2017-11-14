package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.videos.view;

import java.util.List;

import ua.meugen.android.popularmovies.model.db.entity.VideoItem;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.view.MvpView;


public interface MovieVideosView extends MvpView {

    void onVideosLoaded(List<VideoItem> videos);
}

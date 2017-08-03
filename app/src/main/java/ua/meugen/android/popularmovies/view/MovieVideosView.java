package ua.meugen.android.popularmovies.view;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

import ua.meugen.android.popularmovies.model.responses.VideoItemDto;


public interface MovieVideosView extends MvpView {

    void onVideosLoaded(List<VideoItemDto> videos);
}

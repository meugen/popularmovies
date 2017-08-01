package ua.meugen.android.popularmovies.presenter;

import android.databinding.ObservableField;
import android.view.View;

import ua.meugen.android.popularmovies.model.responses.VideoItemDto;
import ua.meugen.android.popularmovies.presenter.listeners.OnClickVideoListener;

public class MovieVideoItemViewModel implements View.OnClickListener {

    public final ObservableField<VideoItemDto> video;
    private final OnClickVideoListener listener;

    public MovieVideoItemViewModel(
            final VideoItemDto dto,
            final OnClickVideoListener listener) {
        video = new ObservableField<>(dto);
        this.listener = listener;
    }

    public void setVideo(final VideoItemDto dto) {
        video.set(dto);
    }

    @Override
    public void onClick(final View view) {
        if (listener != null) {
            listener.onClick(video.get());
        }
    }
}

package ua.meugen.android.popularmovies.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.model.responses.VideoItemDto;
import ua.meugen.android.popularmovies.presenter.listeners.OnClickVideoListener;


public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideoItemViewHolder> {

    private final LayoutInflater inflater;
    private final OnClickVideoListener listener;

    private List<VideoItemDto> videos;

    public VideosAdapter(final Context context, final OnClickVideoListener listener) {
        this.inflater = LayoutInflater
                .from(context);
        this.listener = listener;
        this.videos = Collections.emptyList();
    }

    public void setVideos(final List<VideoItemDto> videos) {
        this.videos = videos;
        notifyDataSetChanged();
    }

    @Override
    public VideoItemViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new VideoItemViewHolder(inflater.inflate(R.layout.item_video, parent, false));
    }

    @Override
    public void onBindViewHolder(final VideoItemViewHolder holder, final int position) {
        holder.bind(videos.get(position));
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class VideoItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name) TextView nameView;

        private VideoItemDto video;

        public VideoItemViewHolder(final View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(final VideoItemDto dto) {
            video = dto;
            nameView.setText(dto.getName());
        }

        @OnClick(R.id.container)
        public void click() {
            if (listener != null) {
                listener.onClickVideo(video);
            }
        }
    }

}

package ua.meugen.android.popularmovies.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.model.dto.VideoItemDto;
import ua.meugen.android.popularmovies.viewmodel.listeners.OnClickVideoListener;


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
        final View view = inflater.inflate(R.layout.item_video, parent, false);
        return new VideoItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final VideoItemViewHolder holder, final int position) {
        holder.bind(videos.get(position));
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class VideoItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView nameView;

        public VideoItemViewHolder(final View itemView) {
            super(itemView);
            nameView = (TextView) itemView.findViewById(R.id.name);
        }

        public void bind(final VideoItemDto dto) {
            nameView.setText(dto.getName());
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View view) {
            callOnClickVideoListener(getAdapterPosition());
        }
    }

}

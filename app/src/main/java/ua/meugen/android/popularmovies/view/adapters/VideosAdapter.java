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
import ua.meugen.android.popularmovies.databinding.ItemVideoBinding;
import ua.meugen.android.popularmovies.model.dto.VideoItemDto;
import ua.meugen.android.popularmovies.viewmodel.listeners.MovieVideoItemViewModel;
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
        return new VideoItemViewHolder(ItemVideoBinding.inflate(inflater, parent, false));
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

        private final ItemVideoBinding binding;

        public VideoItemViewHolder(final ItemVideoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(final VideoItemDto dto) {
            if (binding.getModel() == null) {
                binding.setModel(new MovieVideoItemViewModel(dto, listener));
            } else {
                binding.getModel().setVideo(dto);
            }
        }
    }

}

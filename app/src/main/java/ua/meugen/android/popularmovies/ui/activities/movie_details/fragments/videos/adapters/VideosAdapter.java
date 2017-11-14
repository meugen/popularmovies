package ua.meugen.android.popularmovies.ui.activities.movie_details.fragments.videos.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import ua.meugen.android.popularmovies.databinding.ItemVideoBinding;
import ua.meugen.android.popularmovies.model.db.entity.VideoItem;
import ua.meugen.android.popularmovies.ui.activities.base.BaseActivityModule;


public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideoItemViewHolder> {

    private final LayoutInflater inflater;
    private final OnClickVideoListener listener;

    private List<VideoItem> videos;

    @Inject
    public VideosAdapter(
            @Named(BaseActivityModule.ACTIVITY_CONTEXT) final Context context,
            final OnClickVideoListener listener) {
        this.inflater = LayoutInflater
                .from(context);
        this.listener = listener;
        this.videos = Collections.emptyList();
    }

    public void swapVideos(final List<VideoItem> videos) {
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
            binding.setHolder(this);
        }

        public void bind(final VideoItem dto) {
            binding.setVideo(dto);
        }

        public void click() {
            if (listener != null) {
                listener.onClickVideo(binding.getVideo());
            }
        }
    }

}

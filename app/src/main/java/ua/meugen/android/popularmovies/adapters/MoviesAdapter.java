package ua.meugen.android.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.dto.MovieItemDto;
import ua.meugen.android.popularmovies.images.FileSize;
import ua.meugen.android.popularmovies.images.ImageLoader;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private final LayoutInflater inflater;
    private final ImageLoader loader;

    private List<MovieItemDto> items;

    private OnMovieClickListener onMovieClickListener;

    public MoviesAdapter(final Context context, final List<MovieItemDto> items) {
        this.inflater = LayoutInflater.from(context);
        this.loader = ImageLoader.from(context);

        this.items = items;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(
            final ViewGroup parent, final int viewType) {
        final View view = inflater.inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(loader, view);
    }

    @Override
    public void onBindViewHolder(
            final MovieViewHolder holder,
            final int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public OnMovieClickListener getOnMovieClickListener() {
        return onMovieClickListener;
    }

    public void setOnMovieClickListener(final OnMovieClickListener onMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener;
    }

    public void setItems(final List<MovieItemDto> list) {
        this.items = list;
        this.notifyDataSetChanged();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private final ImageLoader loader;
        private final ImageView poster;

        public MovieViewHolder(final ImageLoader loader, final View itemView) {
            super(itemView);
            this.loader = loader;
            poster = (ImageView) itemView.findViewById(R.id.poster);
        }

        public void bind(final MovieItemDto item) {
            loader.load(FileSize.w(500), item.getPosterPath()).into(poster);
            poster.setOnClickListener(this);
        }

        @Override
        public void onClick(final View view) {
            if (onMovieClickListener != null) {
                onMovieClickListener.onMovieClick(items.get(getAdapterPosition()));
            }
        }
    }
}

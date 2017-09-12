package ua.meugen.android.popularmovies.ui.activities.movies.fragment.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import ua.meugen.android.popularmovies.databinding.ItemMovieBinding;
import ua.meugen.android.popularmovies.model.responses.MovieItemDto;
import ua.meugen.android.popularmovies.ui.utils.images.FileSize;
import ua.meugen.android.popularmovies.ui.utils.images.ImageLoader;
import ua.meugen.android.popularmovies.ui.activities.movies.fragment.listeners.OnMovieClickListener;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private final LayoutInflater inflater;
    private final OnMovieClickListener listener;

    private List<MovieItemDto> movies;

    public MoviesAdapter(
            @NonNull final Context context,
            @NonNull final OnMovieClickListener listener) {
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
        this.movies = Collections.emptyList();
    }

    public void setMovies(final List<MovieItemDto> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(
            final ViewGroup parent, final int viewType) {
        return new MovieViewHolder(ItemMovieBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(
            final MovieViewHolder holder,
            final int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        private final ItemMovieBinding binding;

        private int movieId;

        public MovieViewHolder(final ItemMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.setHolder(this);
        }

        public void bind(final MovieItemDto movie) {
            movieId = movie.getId();
            ImageLoader.from(itemView.getContext())
                    .load(FileSize.w(500), movie.getPosterPath())
                    .into(binding.poster);
        }

        public void click() {
            if (listener != null) {
                listener.onClickMovie(movieId);
            }
        }
    }
}

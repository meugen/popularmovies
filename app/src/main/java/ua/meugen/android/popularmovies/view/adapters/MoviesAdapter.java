package ua.meugen.android.popularmovies.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import ua.meugen.android.popularmovies.databinding.ItemMovieBinding;
import ua.meugen.android.popularmovies.model.responses.MovieItemDto;
import ua.meugen.android.popularmovies.presenter.MovieItemViewModel;
import ua.meugen.android.popularmovies.presenter.listeners.OnMovieClickListener;

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
        final ItemMovieBinding binding = ItemMovieBinding.inflate(inflater, parent, false);
        return new MovieViewHolder(binding);
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

        public MovieViewHolder(final ItemMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(final MovieItemDto movie) {
            if (binding.getModel() == null) {
                binding.setModel(new MovieItemViewModel(movie, listener));
            } else {
                binding.getModel().setMovie(movie);
            }
        }
    }
}

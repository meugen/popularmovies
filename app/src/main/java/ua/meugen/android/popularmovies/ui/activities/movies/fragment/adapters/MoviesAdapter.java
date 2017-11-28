package ua.meugen.android.popularmovies.ui.activities.movies.fragment.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import ua.meugen.android.popularmovies.app.di.PerFragment;
import ua.meugen.android.popularmovies.databinding.ItemMovieBinding;
import ua.meugen.android.popularmovies.model.db.entity.MovieItem;
import ua.meugen.android.popularmovies.ui.activities.base.BaseActivityModule;

@PerFragment
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private final LayoutInflater inflater;
    private final OnMovieClickListener listener;

    private List<MovieItem> movies;

    @Inject
    MoviesAdapter(
            @Named(BaseActivityModule.ACTIVITY_CONTEXT) final Context context,
            final OnMovieClickListener listener) {
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
        this.movies = Collections.emptyList();
    }

    public void swapMovies(final List<MovieItem> movies) {
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

        MovieViewHolder(final ItemMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.setHolder(this);
        }

        void bind(final MovieItem movie) {
            movieId = movie.id;
            binding.setMovie(movie);
        }

        public void click() {
            if (listener != null) {
                listener.onClickMovie(movieId);
            }
        }
    }
}

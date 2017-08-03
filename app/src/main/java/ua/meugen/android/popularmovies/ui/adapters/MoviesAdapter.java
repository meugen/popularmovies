package ua.meugen.android.popularmovies.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.model.responses.MovieItemDto;
import ua.meugen.android.popularmovies.presenter.images.FileSize;
import ua.meugen.android.popularmovies.presenter.images.ImageLoader;
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
        return new MovieViewHolder(inflater.inflate(
                R.layout.item_movie, parent, false));
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

        @BindView(R.id.poster) ImageView posterView;

        private int movieId;

        public MovieViewHolder(final View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(final MovieItemDto movie) {
            movieId = movie.getId();
            ImageLoader.from(itemView.getContext())
                    .load(FileSize.w(500), movie.getPosterPath())
                    .into(posterView);
        }

        @OnClick(R.id.poster)
        public void click() {
            if (listener != null) {
                listener.onClickMovie(movieId);
            }
        }
    }
}

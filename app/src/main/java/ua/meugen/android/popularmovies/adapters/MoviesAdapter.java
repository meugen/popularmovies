package ua.meugen.android.popularmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.images.FileSize;
import ua.meugen.android.popularmovies.images.ImageLoader;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private final LayoutInflater inflater;
    private final ImageLoader loader;

    private Cursor cursor;

    private OnMovieClickListener onMovieClickListener;

    public MoviesAdapter(final Context context) {
        this.inflater = LayoutInflater.from(context);
        this.loader = ImageLoader.from(context);
    }

    public void swapCursor(final Cursor cursor) {
        if (this.cursor == cursor) {
            return;
        }
        this.cursor = cursor;
        notifyDataSetChanged();
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
        cursor.moveToPosition(position);
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    public OnMovieClickListener getOnMovieClickListener() {
        return onMovieClickListener;
    }

    public void setOnMovieClickListener(final OnMovieClickListener onMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener;
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

        public void bind() {
            loader.load(FileSize.w(500), cursor.getString(1)).into(poster);
            poster.setOnClickListener(this);
        }

        @Override
        public void onClick(final View view) {
            if (onMovieClickListener != null) {
                cursor.moveToPosition(getAdapterPosition());
                onMovieClickListener.onMovieClick(cursor.getInt(0));
            }
        }
    }
}

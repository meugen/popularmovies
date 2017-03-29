package ua.meugen.android.popularmovies.activities;

import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.dto.PagedMoviesDto;
import ua.meugen.android.popularmovies.loaders.AbstractCallbacks;
import ua.meugen.android.popularmovies.loaders.LoaderResult;
import ua.meugen.android.popularmovies.loaders.PopularMoviesLoader;
import ua.meugen.android.popularmovies.loaders.TopRatedMoviesLoader;

public class MainActivity extends AppCompatActivity {

    private static final int LOADER_POPULAR = 1;
    private static final int LOADER_TOP_RATED = 2;

    private final MoviesCallbacks moviesCallbacks = new MoviesCallbacks();

    private View progressBarContainer;
    private View messageContainer;
    private TextView message;
    private RecyclerView recycler;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBarContainer = findViewById(R.id.progress_bar_container);
        messageContainer = findViewById(R.id.message_container);
        message = (TextView) findViewById(R.id.message);
        recycler = (RecyclerView) findViewById(R.id.recycler);
    }

    private class MoviesCallbacks extends AbstractCallbacks<PagedMoviesDto> {

        @Override
        protected void onData(final PagedMoviesDto data) {

        }

        @Override
        protected void onError(final IOException ex) {

        }

        @Override
        protected void onNoNetwork() {

        }

        @Override
        public Loader<LoaderResult<PagedMoviesDto>> onCreateLoader(
                final int id, final Bundle args) {
            if (id == LOADER_POPULAR) {
                return new PopularMoviesLoader(MainActivity.this, args);
            } else if (id == LOADER_TOP_RATED) {
                return new TopRatedMoviesLoader(MainActivity.this, args);
            }
            return null;
        }
    }
}

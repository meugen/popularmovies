package ua.meugen.android.popularmovies.ui.activities.movies;

import android.os.Bundle;

import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.ui.activities.base.BaseActivity;

public class MoviesActivity extends BaseActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
    }
}

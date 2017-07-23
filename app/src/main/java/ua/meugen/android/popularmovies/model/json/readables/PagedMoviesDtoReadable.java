package ua.meugen.android.popularmovies.model.json.readables;

import android.util.JsonReader;

import java.io.IOException;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.model.dto.MovieItemDto;
import ua.meugen.android.popularmovies.model.dto.PagedMoviesDto;
import ua.meugen.android.popularmovies.model.json.JsonReadable;
import ua.meugen.android.popularmovies.model.json.JsonUtils;

public class PagedMoviesDtoReadable extends AbstractResponseReadable<PagedMoviesDto> {

    private final JsonReadable<MovieItemDto> movieItemReadable;

    @Inject
    public PagedMoviesDtoReadable(final JsonReadable<MovieItemDto> movieItemReadable) {
        this.movieItemReadable = movieItemReadable;
    }

    @Override
    public PagedMoviesDto readJson(final JsonReader reader) throws IOException {
        final PagedMoviesDto dto = new PagedMoviesDto();

        reader.beginObject();
        while (reader.hasNext()) {
            final String name = reader.nextName();
            if ("page".equals(name)) {
                dto.setPage(reader.nextInt());
            } else if ("total_results".equals(name)) {
                dto.setTotalResults(reader.nextInt());
            } else if ("total_pages".equals(name)) {
                dto.setTotalPages(reader.nextInt());
            } else if ("results".equals(name)) {
                dto.setResults(JsonUtils.nextList(reader, movieItemReadable));
            } else {
                _readFromJson(reader, name, dto);
            }
        }
        reader.endObject();

        return dto;
    }
}

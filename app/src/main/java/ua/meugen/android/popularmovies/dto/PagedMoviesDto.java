package ua.meugen.android.popularmovies.dto;

import android.util.JsonReader;

import java.io.IOException;
import java.util.List;

import ua.meugen.android.popularmovies.json.JsonReadable;
import ua.meugen.android.popularmovies.json.JsonUtils;

public class PagedMoviesDto {

    public static final JsonReadable<PagedMoviesDto> READABLE
            = new PagedMoviesDtoReadable();

    private int page;
    private int totalResults;
    private int totalPages;
    private List<MovieItemDto> results;

    public int getPage() {
        return page;
    }

    public void setPage(final int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(final int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(final int totalPages) {
        this.totalPages = totalPages;
    }

    public List<MovieItemDto> getResults() {
        return results;
    }

    public void setResults(final List<MovieItemDto> results) {
        this.results = results;
    }
}

class PagedMoviesDtoReadable implements JsonReadable<PagedMoviesDto> {

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
                dto.setResults(JsonUtils.nextList(reader, MovieItemDto.READABLE));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return dto;
    }
}
package ua.meugen.android.popularmovies.json.readables;

import android.util.JsonReader;

import java.io.IOException;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.dto.PagedReviewsDto;
import ua.meugen.android.popularmovies.dto.ReviewItemDto;
import ua.meugen.android.popularmovies.json.JsonReadable;
import ua.meugen.android.popularmovies.json.JsonUtils;

public class PagedReviewsDtoReadable extends AbstractResponseReadable<PagedReviewsDto> {

    private final JsonReadable<ReviewItemDto> reviewItemReadable;

    @Inject
    public PagedReviewsDtoReadable(final JsonReadable<ReviewItemDto> reviewItemReadable) {
        this.reviewItemReadable = reviewItemReadable;
    }

    @Override
    public PagedReviewsDto readJson(final JsonReader reader) throws IOException {
        final PagedReviewsDto dto = new PagedReviewsDto();

        reader.beginObject();
        while (reader.hasNext()) {
            final String name = reader.nextName();
            if ("id".equals(name)) {
                dto.setId(reader.nextInt());
            } else if ("page".equals(name)) {
                dto.setPage(reader.nextInt());
            } else if ("total_pages".equals(name)) {
                dto.setTotalPages(reader.nextInt());
            } else if ("total_results".equals(name)) {
                dto.setTotalResults(reader.nextInt());
            } else if ("results".equals(name)) {
                dto.setResults(JsonUtils.nextList(reader, reviewItemReadable));
            } else {
                _readFromJson(reader, name, dto);
            }
        }
        reader.endObject();

        return dto;
    }
}

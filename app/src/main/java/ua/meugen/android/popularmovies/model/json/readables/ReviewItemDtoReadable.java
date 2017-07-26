package ua.meugen.android.popularmovies.model.json.readables;

import android.util.JsonReader;

import java.io.IOException;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.model.responses.ReviewItemDto;
import ua.meugen.android.popularmovies.model.json.JsonReadable;

public class ReviewItemDtoReadable implements JsonReadable<ReviewItemDto> {

    @Inject
    public ReviewItemDtoReadable() {}

    @Override
    public ReviewItemDto readJson(final JsonReader reader) throws IOException {
        final ReviewItemDto dto = new ReviewItemDto();

        reader.beginObject();
        while (reader.hasNext()) {
            final String name = reader.nextName();
            if ("id".equals(name)) {
                dto.setId(reader.nextString());
            } else if ("author".equals(name)) {
                dto.setAuthor(reader.nextString());
            } else if ("content".equals(name)) {
                dto.setContent(reader.nextString());
            } else if ("url".equals(name)) {
                dto.setUrl(reader.nextString());
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return dto;
    }
}

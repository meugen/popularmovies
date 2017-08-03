package ua.meugen.android.popularmovies.app.json.readables;

import android.util.JsonReader;

import java.io.IOException;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.app.json.JsonReadable;
import ua.meugen.android.popularmovies.model.responses.VideoItemDto;

public class VideoItemDtoReadable implements JsonReadable<VideoItemDto> {

    @Inject
    public VideoItemDtoReadable() {}

    @Override
    public VideoItemDto readJson(final JsonReader reader) throws IOException {
        final VideoItemDto dto = new VideoItemDto();

        reader.beginObject();
        while (reader.hasNext()) {
            final String name = reader.nextName();
            if ("id".equals(name)) {
                dto.setId(reader.nextString());
            } else if ("iso_639_1".equals(name)) {
                dto.setIso6391(reader.nextString());
            } else if ("iso_3166_1".equals(name)) {
                dto.setIso31661(reader.nextString());
            } else if ("key".equals(name)) {
                dto.setKey(reader.nextString());
            } else if ("name".equals(name)) {
                dto.setName(reader.nextString());
            } else if ("site".equals(name)) {
                dto.setSite(reader.nextString());
            } else if ("size".equals(name)) {
                dto.setSize(reader.nextInt());
            } else if ("type".equals(name)) {
                dto.setType(reader.nextString());
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return dto;
    }
}

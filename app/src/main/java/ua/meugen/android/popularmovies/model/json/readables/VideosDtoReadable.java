package ua.meugen.android.popularmovies.model.json.readables;

import android.util.JsonReader;

import java.io.IOException;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.model.json.JsonReadable;
import ua.meugen.android.popularmovies.model.json.JsonUtils;
import ua.meugen.android.popularmovies.model.responses.VideoItemDto;
import ua.meugen.android.popularmovies.model.responses.VideosDto;

public class VideosDtoReadable extends AbstractResponseReadable<VideosDto> {

    private final JsonReadable<VideoItemDto> videoItemReadable;

    @Inject
    public VideosDtoReadable(final JsonReadable<VideoItemDto> videoItemReadable) {
        this.videoItemReadable = videoItemReadable;
    }

    @Override
    public VideosDto readJson(final JsonReader reader) throws IOException {
        final VideosDto dto = new VideosDto();

        reader.beginObject();
        while (reader.hasNext()) {
            final String name = reader.nextName();
            if ("id".equals(name)) {
                dto.setId(reader.nextInt());
            } else if ("results".equals(name)) {
                dto.setResults(JsonUtils.nextList(reader, videoItemReadable));
            } else {
                _readFromJson(reader, name, dto);
            }
        }
        reader.endObject();

        return dto;
    }
}

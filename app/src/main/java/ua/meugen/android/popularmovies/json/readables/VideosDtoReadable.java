package ua.meugen.android.popularmovies.json.readables;

import android.util.JsonReader;

import java.io.IOException;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.dto.VideoItemDto;
import ua.meugen.android.popularmovies.dto.VideosDto;
import ua.meugen.android.popularmovies.json.JsonReadable;
import ua.meugen.android.popularmovies.json.JsonUtils;

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
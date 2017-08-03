package ua.meugen.android.popularmovies.app.json.readables;

import android.util.JsonReader;

import java.io.IOException;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.app.json.JsonReadable;
import ua.meugen.android.popularmovies.app.json.JsonUtils;
import ua.meugen.android.popularmovies.model.responses.MovieItemDto;

public class MovieItemDtoReadable implements JsonReadable<MovieItemDto> {

    @Inject
    public MovieItemDtoReadable() {}

    @Override
    public MovieItemDto readJson(final JsonReader reader) throws IOException {
        final MovieItemDto dto = new MovieItemDto();

        reader.beginObject();
        while (reader.hasNext()) {
            final String name = reader.nextName();
            if ("poster_path".equals(name)) {
                dto.setPosterPath(reader.nextString());
            } else if ("adult".equals(name)) {
                dto.setAdult(reader.nextBoolean());
            } else if ("overview".equals(name)) {
                dto.setOverview(reader.nextString());
            } else if ("release_date".equals(name)) {
                dto.setReleaseDate(JsonUtils.nextDate(reader));
            } else if ("genre_ids".equals(name)) {
                dto.setGenreIds(JsonUtils.nextList(reader, JsonUtils.INTEGER_READABLE));
            } else if ("id".equals(name)) {
                dto.setId(reader.nextInt());
            } else if ("original_title".equals(name)) {
                dto.setOriginalTitle(reader.nextString());
            } else if ("original_language".equals(name)) {
                dto.setOriginalLanguage(reader.nextString());
            } else if ("title".equals(name)) {
                dto.setTitle(reader.nextString());
            } else if ("backdrop_path".equals(name)) {
                dto.setBackdropPath(reader.nextString());
            } else if ("popularity".equals(name)) {
                dto.setPopularity(reader.nextDouble());
            } else if ("vote_count".equals(name)) {
                dto.setVoteCount(reader.nextInt());
            } else if ("video".equals(name)) {
                dto.setVideo(reader.nextBoolean());
            } else if ("vote_average".equals(name)) {
                dto.setVoteAverage(reader.nextDouble());
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return dto;
    }
}

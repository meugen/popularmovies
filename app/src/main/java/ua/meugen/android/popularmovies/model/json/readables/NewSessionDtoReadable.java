package ua.meugen.android.popularmovies.model.json.readables;

import android.util.JsonReader;

import java.io.IOException;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.model.dto.NewSessionDto;

public class NewSessionDtoReadable extends AbstractResponseReadable<NewSessionDto> {

    @Inject
    public NewSessionDtoReadable() {}

    @Override
    public NewSessionDto readJson(final JsonReader reader) throws IOException {
        final NewSessionDto dto = new NewSessionDto();

        reader.beginObject();
        while (reader.hasNext()) {
            final String name = reader.nextName();
            if ("success".equals(name)) {
                dto.setSuccess(reader.nextBoolean());
            } else if ("session_id".equals(name)) {
                dto.setSessionId(reader.nextString());
            } else {
                _readFromJson(reader, name, dto);
            }
        }
        reader.endObject();

        return dto;
    }
}

package ua.meugen.android.popularmovies.model.json.readables;

import android.util.JsonReader;

import java.io.IOException;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.model.responses.NewGuestSessionDto;
import ua.meugen.android.popularmovies.model.json.JsonUtils;

public class NewGuestSessionDtoReadable extends AbstractResponseReadable<NewGuestSessionDto> {

    @Inject
    public NewGuestSessionDtoReadable() {}

    @Override
    public NewGuestSessionDto readJson(final JsonReader reader) throws IOException {
        final NewGuestSessionDto dto = new NewGuestSessionDto();

        reader.beginObject();
        while (reader.hasNext()) {
            final String name = reader.nextName();
            if ("success".equals(name)) {
                dto.setSuccess(reader.nextBoolean());
            } else if ("guest_session_id".equals(name)) {
                dto.setGuestSessionId(reader.nextString());
            } else if ("expires_at".equals(name)) {
                dto.setExpiresAt(JsonUtils.nextDateTime(reader));
            } else {
                _readFromJson(reader, name, dto);
            }
        }
        reader.endObject();

        return dto;
    }
}

package ua.meugen.android.popularmovies.model.json.readables;

import android.util.JsonReader;

import java.io.IOException;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.model.responses.NewTokenDto;
import ua.meugen.android.popularmovies.model.json.JsonUtils;

public class NewTokenDtoReadable extends AbstractResponseReadable<NewTokenDto> {

    @Inject
    public NewTokenDtoReadable() {}

    @Override
    public NewTokenDto readJson(final JsonReader reader) throws IOException {
        final NewTokenDto dto = new NewTokenDto();

        reader.beginObject();
        while (reader.hasNext()) {
            final String name = reader.nextName();
            if ("success".equals(name)) {
                dto.setSuccess(reader.nextBoolean());
            } else if ("expires_at".equals(name)) {
                dto.setExpiresAt(JsonUtils.nextDateTime(reader));
            } else if ("request_token".equals(name)) {
                dto.setToken(reader.nextString());
            } else {
                _readFromJson(reader, name, dto);
            }
        }
        reader.endObject();

        return dto;
    }
}

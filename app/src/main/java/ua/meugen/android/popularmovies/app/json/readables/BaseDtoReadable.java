package ua.meugen.android.popularmovies.app.json.readables;

import android.util.JsonReader;

import java.io.IOException;

import javax.inject.Inject;

import ua.meugen.android.popularmovies.model.responses.BaseDto;

public class BaseDtoReadable extends AbstractResponseReadable<BaseDto> {

    @Inject
    public BaseDtoReadable() {}

    @Override
    public BaseDto readJson(final JsonReader reader) throws IOException {
        final BaseDto dto = new BaseDto();

        reader.beginObject();
        while (reader.hasNext()) {
            final String name = reader.nextName();
            _readFromJson(reader, name, dto);
        }
        reader.endObject();

        return dto;
    }
}

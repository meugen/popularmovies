package ua.meugen.android.popularmovies.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ua.meugen.android.popularmovies.json.JsonReadable;
import ua.meugen.android.popularmovies.json.JsonUtils;

public class PagedMoviesDto implements Parcelable {

    public static final Creator<PagedMoviesDto> CREATOR
            = new PagedMoviesDtoCreator();
    public static final JsonReadable<PagedMoviesDto> READABLE
            = new PagedMoviesDtoReadable();

    private int page;
    private int totalResults;
    private int totalPages;
    private List<MovieItemDto> results;

    public int getPage() {
        return page;
    }

    public void setPage(final int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(final int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(final int totalPages) {
        this.totalPages = totalPages;
    }

    public List<MovieItemDto> getResults() {
        return results;
    }

    public void setResults(final List<MovieItemDto> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int flags) {
        parcel.writeInt(page);
        parcel.writeInt(totalResults);
        parcel.writeInt(totalPages);
        parcel.writeTypedList(results);
    }
}

class PagedMoviesDtoReadable implements JsonReadable<PagedMoviesDto> {

    @Override
    public PagedMoviesDto readJson(final JsonReader reader) throws IOException {
        final PagedMoviesDto dto = new PagedMoviesDto();

        reader.beginObject();
        while (reader.hasNext()) {
            final String name = reader.nextName();
            if ("page".equals(name)) {
                dto.setPage(reader.nextInt());
            } else if ("total_results".equals(name)) {
                dto.setTotalResults(reader.nextInt());
            } else if ("total_pages".equals(name)) {
                dto.setTotalPages(reader.nextInt());
            } else if ("results".equals(name)) {
                dto.setResults(JsonUtils.nextList(reader, MovieItemDto.READABLE));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return dto;
    }
}

class PagedMoviesDtoCreator implements Parcelable.Creator<PagedMoviesDto> {

    @Override
    public PagedMoviesDto createFromParcel(final Parcel parcel) {
        final PagedMoviesDto dto = new PagedMoviesDto();
        dto.setPage(parcel.readInt());
        dto.setTotalResults(parcel.readInt());
        dto.setTotalPages(parcel.readInt());
        final List<MovieItemDto> results = new ArrayList<>();
        parcel.readTypedList(results, MovieItemDto.CREATOR);
        dto.setResults(results);
        return dto;
    }

    @Override
    public PagedMoviesDto[] newArray(final int size) {
        return new PagedMoviesDto[size];
    }
}
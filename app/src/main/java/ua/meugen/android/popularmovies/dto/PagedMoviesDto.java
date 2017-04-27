package ua.meugen.android.popularmovies.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ua.meugen.android.popularmovies.json.JsonReadable;
import ua.meugen.android.popularmovies.json.JsonUtils;

public class PagedMoviesDto extends BaseResponse implements Parcelable {

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
        _writeToParcel(parcel);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        final PagedMoviesDto that = (PagedMoviesDto) o;

        if (page != that.page) return false;
        if (totalResults != that.totalResults) return false;
        if (totalPages != that.totalPages) return false;
        return results != null ? results.equals(that.results) : that.results == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + page;
        result = 31 * result + totalResults;
        result = 31 * result + totalPages;
        result = 31 * result + (results != null ? results.hashCode() : 0);
        return result;
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
                dto._readFromJson(reader, name);
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
        dto._readFromParcel(parcel);
        return dto;
    }

    @Override
    public PagedMoviesDto[] newArray(final int size) {
        return new PagedMoviesDto[size];
    }
}
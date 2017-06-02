package ua.meugen.android.popularmovies.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;


public class PagedReviewsDto extends BaseResponse implements Parcelable {

    public static final Creator<PagedReviewsDto> CREATOR
            = new PagedReviewsDtoCreator();

    private int id;
    private int page;
    private int totalPages;
    private int totalResults;
    private List<ReviewItemDto> results;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(final int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(final int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(final int totalResults) {
        this.totalResults = totalResults;
    }

    public List<ReviewItemDto> getResults() {
        return results;
    }

    public void setResults(final List<ReviewItemDto> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int flags) {
        parcel.writeInt(this.id);
        parcel.writeInt(this.page);
        parcel.writeInt(this.totalPages);
        parcel.writeInt(this.totalResults);
        parcel.writeTypedList(this.results);
        _writeToParcel(parcel);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        final PagedReviewsDto that = (PagedReviewsDto) o;

        if (id != that.id) return false;
        if (page != that.page) return false;
        if (totalPages != that.totalPages) return false;
        if (totalResults != that.totalResults) return false;
        return results != null ? results.equals(that.results) : that.results == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id;
        result = 31 * result + page;
        result = 31 * result + totalPages;
        result = 31 * result + totalResults;
        result = 31 * result + (results != null ? results.hashCode() : 0);
        return result;
    }
}

class PagedReviewsDtoCreator implements Parcelable.Creator<PagedReviewsDto> {

    @Override
    public PagedReviewsDto createFromParcel(final Parcel parcel) {
        final PagedReviewsDto dto = new PagedReviewsDto();
        dto.setId(parcel.readInt());
        dto.setPage(parcel.readInt());
        dto.setTotalPages(parcel.readInt());
        dto.setTotalResults(parcel.readInt());
        final List<ReviewItemDto> results = new ArrayList<>();
        parcel.readTypedList(results, ReviewItemDto.CREATOR);
        dto.setResults(results);
        dto._readFromParcel(parcel);
        return dto;
    }

    @Override
    public PagedReviewsDto[] newArray(final int size) {
        return new PagedReviewsDto[size];
    }
}
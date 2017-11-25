package ua.meugen.android.popularmovies.model.network.resp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ua.meugen.android.popularmovies.model.db.entity.MovieItem;

public class PagedMoviesResponse extends BaseResponse {

    @SerializedName("page")
    private int page;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("results")
    private List<MovieItem> results;

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

    public List<MovieItem> getResults() {
        return results;
    }

    public void setResults(final List<MovieItem> results) {
        this.results = results;
    }
}
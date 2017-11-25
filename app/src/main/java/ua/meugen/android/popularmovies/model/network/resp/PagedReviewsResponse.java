package ua.meugen.android.popularmovies.model.network.resp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ua.meugen.android.popularmovies.model.db.entity.ReviewItem;


public class PagedReviewsResponse extends BaseResponse {

    @SerializedName("id")
    private int id;
    @SerializedName("page")
    private int page;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("results")
    private List<ReviewItem> results;

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

    public List<ReviewItem> getResults() {
        return results;
    }

    public void setResults(final List<ReviewItem> results) {
        this.results = results;
    }
}
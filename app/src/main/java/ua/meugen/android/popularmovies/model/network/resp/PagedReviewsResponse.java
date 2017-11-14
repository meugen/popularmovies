package ua.meugen.android.popularmovies.model.network.resp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ua.meugen.android.popularmovies.model.db.entity.ReviewItem;


public class PagedReviewsResponse extends BaseResponse {

    @SerializedName("id")
    public int id;
    @SerializedName("page")
    public int page;
    @SerializedName("total_pages")
    public int totalPages;
    @SerializedName("total_results")
    public int totalResults;
    @SerializedName("results")
    public List<ReviewItem> results;

//    @Override
//    public boolean equals(final Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        if (!super.equals(o)) return false;
//
//        final PagedReviewsResponse that = (PagedReviewsResponse) o;
//
//        if (id != that.id) return false;
//        if (page != that.page) return false;
//        if (totalPages != that.totalPages) return false;
//        if (totalResults != that.totalResults) return false;
//        return results != null ? results.equals(that.results) : that.results == null;
//
//    }
//
//    @Override
//    public int hashCode() {
//        int result = super.hashCode();
//        result = 31 * result + id;
//        result = 31 * result + page;
//        result = 31 * result + totalPages;
//        result = 31 * result + totalResults;
//        result = 31 * result + (results != null ? results.hashCode() : 0);
//        return result;
//    }
}
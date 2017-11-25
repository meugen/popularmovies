package ua.meugen.android.popularmovies.model.network.resp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ua.meugen.android.popularmovies.model.db.entity.VideoItem;


public class VideosResponse extends BaseResponse {

    @SerializedName("id")
    private int id;
    @SerializedName("results")
    private List<VideoItem> results;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public List<VideoItem> getResults() {
        return results;
    }

    public void setResults(final List<VideoItem> results) {
        this.results = results;
    }
}
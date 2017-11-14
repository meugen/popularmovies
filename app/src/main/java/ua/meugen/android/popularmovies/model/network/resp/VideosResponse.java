package ua.meugen.android.popularmovies.model.network.resp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ua.meugen.android.popularmovies.model.db.entity.VideoItem;


public class VideosResponse extends BaseResponse {

    @SerializedName("id")
    public int id;
    @SerializedName("results")
    public List<VideoItem> results;

//    @Override
//    public boolean equals(final Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        if (!super.equals(o)) return false;
//
//        final VideosResponse videosDto = (VideosResponse) o;
//
//        if (id != videosDto.id) return false;
//        return results != null ? results.equals(videosDto.results) : videosDto.results == null;
//
//    }
//
//    @Override
//    public int hashCode() {
//        int result = super.hashCode();
//        result = 31 * result + id;
//        result = 31 * result + (results != null ? results.hashCode() : 0);
//        return result;
//    }
}
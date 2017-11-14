package ua.meugen.android.popularmovies.model.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "videos")
public class VideoItem {

    @PrimaryKey
    @NonNull
    public String id;
    @SerializedName("iso_639_1")
    public String iso6391;
    @SerializedName("iso_3166_1")
    public String iso31661;
    public String key;
    public String name;
    public String site;
    public int size;
    public String type;
    @Expose(serialize = false, deserialize = false)
    @ColumnInfo(name = "movie_id")
    public int movieId;

//    @Override
//    public boolean equals(final Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        final VideoItem that = (VideoItem) o;
//
//        if (size != that.size) return false;
//        if (id != null ? !id.equals(that.id) : that.id != null) return false;
//        if (iso6391 != null ? !iso6391.equals(that.iso6391) : that.iso6391 != null) return false;
//        if (iso31661 != null ? !iso31661.equals(that.iso31661) : that.iso31661 != null)
//            return false;
//        if (key != null ? !key.equals(that.key) : that.key != null) return false;
//        if (name != null ? !name.equals(that.name) : that.name != null) return false;
//        if (site != null ? !site.equals(that.site) : that.site != null) return false;
//        return type != null ? type.equals(that.type) : that.type == null;
//
//    }
//
//    @Override
//    public int hashCode() {
//        int result = id != null ? id.hashCode() : 0;
//        result = 31 * result + (iso6391 != null ? iso6391.hashCode() : 0);
//        result = 31 * result + (iso31661 != null ? iso31661.hashCode() : 0);
//        result = 31 * result + (key != null ? key.hashCode() : 0);
//        result = 31 * result + (name != null ? name.hashCode() : 0);
//        result = 31 * result + (site != null ? site.hashCode() : 0);
//        result = 31 * result + size;
//        result = 31 * result + (type != null ? type.hashCode() : 0);
//        return result;
//    }
}
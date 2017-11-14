package ua.meugen.android.popularmovies.model.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;

@Entity(tableName = "reviews")
public class ReviewItem {

    @PrimaryKey
    @NonNull
    public String id;
    public String author;
    public String content;
    public String url;
    @Expose(serialize = false, deserialize = false)
    @ColumnInfo(name = "movie_id")
    public int movieId;

//    @Override
//    public boolean equals(final Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        final ReviewItem that = (ReviewItem) o;
//
//        if (id != null ? !id.equals(that.id) : that.id != null) return false;
//        if (author != null ? !author.equals(that.author) : that.author != null) return false;
//        if (content != null ? !content.equals(that.content) : that.content != null) return false;
//        return url != null ? url.equals(that.url) : that.url == null;
//
//    }
//
//    @Override
//    public int hashCode() {
//        int result = id != null ? id.hashCode() : 0;
//        result = 31 * result + (author != null ? author.hashCode() : 0);
//        result = 31 * result + (content != null ? content.hashCode() : 0);
//        result = 31 * result + (url != null ? url.hashCode() : 0);
//        return result;
//    }
}
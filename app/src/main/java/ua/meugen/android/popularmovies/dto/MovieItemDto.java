package ua.meugen.android.popularmovies.dto;

import android.util.JsonReader;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import ua.meugen.android.popularmovies.json.JsonReadable;
import ua.meugen.android.popularmovies.json.JsonUtils;

/**
 * Created by meugen on 28.03.17.
 */

public class MovieItemDto {

    public static final JsonReadable<MovieItemDto> READABLE
            = new MovieItemDtoReadable();

    private String posterPath;
    private boolean adult;
    private String overview;
    private Date releaseDate;
    private List<Integer> genreIds;
    private int id;
    private String originalTitle;
    private String originalLanguage;
    private String title;
    private String backdropPath;
    private double popularity;
    private int voteCount;
    private boolean video;
    private double voteAverage;

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(final String posterPath) {
        this.posterPath = posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(final boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(final String overview) {
        this.overview = overview;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(final Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(final List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(final String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(final String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(final String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(final double popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(final int voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(final boolean video) {
        this.video = video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(final double voteAverage) {
        this.voteAverage = voteAverage;
    }
}

class MovieItemDtoReadable implements JsonReadable<MovieItemDto> {

    @Override
    public MovieItemDto readJson(final JsonReader reader) throws IOException {
        final MovieItemDto dto = new MovieItemDto();

        reader.beginObject();
        while (reader.hasNext()) {
            final String name = reader.nextName();
            if ("poster_path".equals(name)) {
                dto.setPosterPath(reader.nextString());
            } else if ("adult".equals(name)) {
                dto.setAdult(reader.nextBoolean());
            } else if ("overview".equals(name)) {
                dto.setOverview(reader.nextString());
            } else if ("release_date".equals(name)) {
                dto.setReleaseDate(JsonUtils.nextDate(reader));
            } else if ("genre_ids".equals(name)) {
                dto.setGenreIds(JsonUtils.nextList(reader, JsonUtils.INTEGER_READABLE));
            } else if ("id".equals(name)) {
                dto.setId(reader.nextInt());
            } else if ("original_title".equals(name)) {
                dto.setOriginalTitle(reader.nextString());
            } else if ("original_language".equals(name)) {
                dto.setOriginalLanguage(reader.nextString());
            } else if ("title".equals(name)) {
                dto.setTitle(reader.nextString());
            } else if ("backdrop_path".equals(name)) {
                dto.setBackdropPath(reader.nextString());
            } else if ("popularity".equals(name)) {
                dto.setPopularity(reader.nextDouble());
            } else if ("vote_count".equals(name)) {
                dto.setVoteCount(reader.nextInt());
            } else if ("video".equals(name)) {
                dto.setVideo(reader.nextBoolean());
            } else if ("vote_average".equals(name)) {
                dto.setVoteAverage(reader.nextDouble());
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return dto;
    }
}

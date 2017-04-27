package ua.meugen.android.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import ua.meugen.android.popularmovies.dto.MovieItemDto;
import ua.meugen.android.popularmovies.dto.PagedMoviesDto;
import ua.meugen.android.popularmovies.dto.PagedReviewsDto;
import ua.meugen.android.popularmovies.dto.ReviewItemDto;
import ua.meugen.android.popularmovies.dto.VideoItemDto;
import ua.meugen.android.popularmovies.dto.VideosDto;

@RunWith(AndroidJUnit4.class)
public class ParcelablesTest {

    private MovieItemDto createMovieItemDto() {
        final MovieItemDto dto = new MovieItemDto();
        dto.setId(1);
        dto.setAdult(true);
        dto.setBackdropPath("backdropPath");
        dto.setGenreIds(new ArrayList<>(Arrays.asList(1, 2)));
        dto.setOriginalLanguage("originalLanguage");
        dto.setOriginalTitle("originalTitle");
        dto.setOverview("overview");
        dto.setPopularity(1.5);
        dto.setPosterPath("posterPath");
        dto.setReleaseDate(new Date(System.currentTimeMillis()));
        dto.setTitle("title");
        dto.setVideo(true);
        dto.setVoteAverage(5.6);
        dto.setVoteCount(5);
        return dto;
    }

    @Test
    public void testMoveItemDtoParcelable() throws Exception {
        testOneParcelable(createMovieItemDto());
    }

    @Test
    public void testPagedMoviesDtoParcelable() throws Exception {
        final PagedMoviesDto dto = new PagedMoviesDto();
        dto.setPage(1);
        dto.setResults(new ArrayList<>(Collections.singleton(createMovieItemDto())));
        dto.setTotalResults(1);
        dto.setTotalPages(1);
        testOneParcelable(dto);
    }

    private ReviewItemDto createReviewItemDto() {
        final ReviewItemDto dto = new ReviewItemDto();
        dto.setAuthor("author");
        dto.setUrl("url");
        dto.setContent("content");
        dto.setId("id");
        return dto;
    }

    @Test
    public void testReviewItemDtoParcelable() throws Exception {
        testOneParcelable(createReviewItemDto());
    }

    @Test
    public void testPagedReviewsDtoParcelable() throws Exception {
        final PagedReviewsDto dto = new PagedReviewsDto();
        dto.setResults(new ArrayList<>(Collections.singleton(createReviewItemDto())));
        dto.setTotalPages(1);
        dto.setTotalResults(1);
        dto.setPage(1);
        dto.setId(1);
        testOneParcelable(dto);
    }

    private VideoItemDto createVideoItemDto() {
        final VideoItemDto dto = new VideoItemDto();
        dto.setIso31661("iso31661");
        dto.setType("type");
        dto.setId("id");
        dto.setSize(1024);
        dto.setSite("Youtube");
        dto.setIso6391("iso6391");
        dto.setKey("key");
        dto.setName("name");
        return dto;
    }

    @Test
    public void testMovieItemDtoParcelable() throws Exception {
        testOneParcelable(createVideoItemDto());
    }

    @Test
    public void testVideosDtoParcelable() throws Exception {
        final VideosDto dto = new VideosDto();
        dto.setId(1);
        dto.setResults(new ArrayList<>(Collections.singleton(createVideoItemDto())));
        testOneParcelable(dto);
    }

    private <T extends Parcelable> void testOneParcelable(
            final T data) throws Exception {
        final Parcel parcel = Parcel.obtain();
        data.writeToParcel(parcel, 0);
        final Field fldCreator = data.getClass().getField("CREATOR");
        final Parcelable.Creator<T> creator = (Parcelable.Creator<T>) fldCreator.get(null);
        parcel.setDataPosition(0);
        final T newData = creator.createFromParcel(parcel);
        Assert.assertEquals(data, newData);
        Assert.assertEquals(data.hashCode(), newData.hashCode());
    }
}

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

import ua.meugen.android.popularmovies.model.db.entity.MovieItem;
import ua.meugen.android.popularmovies.model.db.entity.ReviewItem;
import ua.meugen.android.popularmovies.model.network.resp.NewGuestSessionResponse;
import ua.meugen.android.popularmovies.model.network.resp.NewSessionResponse;
import ua.meugen.android.popularmovies.model.network.resp.NewTokenResponse;
import ua.meugen.android.popularmovies.model.network.resp.PagedMoviesResponse;
import ua.meugen.android.popularmovies.model.network.resp.PagedReviewsResponse;
import ua.meugen.android.popularmovies.model.db.entity.VideoItem;
import ua.meugen.android.popularmovies.model.network.resp.VideosResponse;

@RunWith(AndroidJUnit4.class)
public class ParcelablesTest {

    private MovieItem createMovieItemDto() {
        final MovieItem dto = new MovieItem();
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
        final PagedMoviesResponse dto = new PagedMoviesResponse();
        dto.setPage(1);
        dto.setResults(new ArrayList<>(Collections.singleton(createMovieItemDto())));
        dto.setTotalResults(1);
        dto.setTotalPages(1);
        testOneParcelable(dto);
    }

    private ReviewItem createReviewItemDto() {
        final ReviewItem dto = new ReviewItem();
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
        final PagedReviewsResponse dto = new PagedReviewsResponse();
        dto.setResults(new ArrayList<>(Collections.singleton(createReviewItemDto())));
        dto.setTotalPages(1);
        dto.setTotalResults(1);
        dto.setPage(1);
        dto.setId(1);
        testOneParcelable(dto);
    }

    private VideoItem createVideoItemDto() {
        final VideoItem dto = new VideoItem();
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
        final VideosResponse dto = new VideosResponse();
        dto.setId(1);
        dto.setResults(new ArrayList<>(Collections.singleton(createVideoItemDto())));
        testOneParcelable(dto);
    }

    @Test
    public void testNewTokenDtoParcelable() throws Exception {
        final NewTokenResponse dto = new NewTokenResponse();
        dto.setExpiresAt(new Date(System.currentTimeMillis()));
        dto.setToken("token");
        dto.setSuccess(true);
        testOneParcelable(dto);
    }

    @Test
    public void testNewSessionDtoParcelable() throws Exception {
        final NewSessionResponse dto = new NewSessionResponse();
        dto.setSessionId("session_id");
        dto.setSuccess(true);
        testOneParcelable(dto);
    }

    @Test
    public void testNewGuestSessionDtoParcelable() throws Exception {
        final NewGuestSessionResponse dto = new NewGuestSessionResponse();
        dto.setExpiresAt(new Date(System.currentTimeMillis()));
        dto.setGuestSessionId("guest_session_id");
        dto.setSuccess(true);
        testOneParcelable(dto);
    }

    @Test
    public void testBaseDtoParcelable() throws Exception {
        testOneParcelable(new BaseDto());
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

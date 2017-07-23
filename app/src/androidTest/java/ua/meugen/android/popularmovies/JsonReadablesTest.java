package ua.meugen.android.popularmovies;

import android.support.test.runner.AndroidJUnit4;
import android.util.JsonReader;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.StringReader;
import java.util.Calendar;
import java.util.TimeZone;

import ua.meugen.android.popularmovies.model.dto.MovieItemDto;
import ua.meugen.android.popularmovies.model.dto.NewGuestSessionDto;
import ua.meugen.android.popularmovies.model.dto.NewSessionDto;
import ua.meugen.android.popularmovies.model.dto.NewTokenDto;
import ua.meugen.android.popularmovies.model.dto.PagedMoviesDto;
import ua.meugen.android.popularmovies.model.dto.PagedReviewsDto;
import ua.meugen.android.popularmovies.model.dto.ReviewItemDto;
import ua.meugen.android.popularmovies.model.dto.VideoItemDto;
import ua.meugen.android.popularmovies.model.dto.VideosDto;
import ua.meugen.android.popularmovies.model.json.JsonReadable;
import ua.meugen.android.popularmovies.model.json.readables.MovieItemDtoReadable;
import ua.meugen.android.popularmovies.model.json.readables.NewGuestSessionDtoReadable;
import ua.meugen.android.popularmovies.model.json.readables.NewSessionDtoReadable;
import ua.meugen.android.popularmovies.model.json.readables.NewTokenDtoReadable;
import ua.meugen.android.popularmovies.model.json.readables.PagedMoviesDtoReadable;
import ua.meugen.android.popularmovies.model.json.readables.PagedReviewsDtoReadable;
import ua.meugen.android.popularmovies.model.json.readables.ReviewItemDtoReadable;
import ua.meugen.android.popularmovies.model.json.readables.VideoItemDtoReadable;
import ua.meugen.android.popularmovies.model.json.readables.VideosDtoReadable;
import ua.meugen.android.popularmovies.utils.IOUtils;

@RunWith(AndroidJUnit4.class)
public class JsonReadablesTest {

    private static JsonReadable<MovieItemDto> movieItemReadable;
    private static JsonReadable<PagedMoviesDto> pagedMoviesReadable;
    private static JsonReadable<VideoItemDto> videoItemReadable;
    private static JsonReadable<VideosDto> videosReadable;
    private static JsonReadable<ReviewItemDto> reviewItemReadable;
    private static JsonReadable<PagedReviewsDto> pagedReviewsReadable;
    private static JsonReadable<NewTokenDto> newTokenReadable;
    private static JsonReadable<NewSessionDto> newSessionReadable;
    private static JsonReadable<NewGuestSessionDto> newGuestSessionReadable;

    @BeforeClass
    public static void setup() {
        movieItemReadable = new MovieItemDtoReadable();
        pagedMoviesReadable = new PagedMoviesDtoReadable(movieItemReadable);
        videoItemReadable = new VideoItemDtoReadable();
        videosReadable = new VideosDtoReadable(videoItemReadable);
        reviewItemReadable = new ReviewItemDtoReadable();
        pagedReviewsReadable = new PagedReviewsDtoReadable(reviewItemReadable);
        newTokenReadable = new NewTokenDtoReadable();
        newSessionReadable = new NewSessionDtoReadable();
        newGuestSessionReadable = new NewGuestSessionDtoReadable();
    }

    private static final String MOVIE_ITEM_JSON = "{\n" +
            "      \"poster_path\": \"/9O7gLzmreU0nGkIB6K3BsJbzvNv.jpg\",\n" +
            "      \"adult\": false,\n" +
            "      \"overview\": \"Framed in the 1940s for the double murder of his wife and her lover, upstanding banker Andy Dufresne begins a new life at the Shawshank prison, where he puts his accounting skills to work for an amoral warden. During his long stretch in prison, Dufresne comes to be admired by the other inmates -- including an older prisoner named Red -- for his integrity and unquenchable sense of hope.\",\n" +
            "      \"release_date\": \"1994-09-23\",\n" +
            "      \"genre_ids\": [\n" +
            "        18,\n" +
            "        80\n" +
            "      ],\n" +
            "      \"id\": 278,\n" +
            "      \"original_title\": \"The Shawshank Redemption\",\n" +
            "      \"original_language\": \"en\",\n" +
            "      \"title\": \"The Shawshank Redemption\",\n" +
            "      \"backdrop_path\": \"/j9XKiZrVeViAixVRzCta7h1VU9W.jpg\",\n" +
            "      \"popularity\": 9.816748,\n" +
            "      \"vote_count\": 6949,\n" +
            "      \"video\": false,\n" +
            "      \"vote_average\": 8.4\n" +
            "    }";
    private static final String PAGED_MOVIES_JSON = "{\"page\":1, \"results\":["
            + MOVIE_ITEM_JSON + "], \"total_pages\":1, \"total_results\":1}";

    @Test
    public void testMovieItemJsonReadable() throws Exception {
        JsonReader reader = null;
        try {
            reader = new JsonReader(new StringReader(MOVIE_ITEM_JSON));
            final MovieItemDto dto = movieItemReadable.readJson(reader);
            Assert.assertNotNull(dto);
            Assert.assertEquals("/9O7gLzmreU0nGkIB6K3BsJbzvNv.jpg", dto.getPosterPath());
            Assert.assertFalse(dto.isAdult());
            Assert.assertEquals(dto.getGenreIds().size(), 2);
            Assert.assertEquals((int) dto.getGenreIds().get(0), 18);
            Assert.assertEquals((int) dto.getGenreIds().get(1), 80);
            Assert.assertEquals(dto.getId(), 278);
            Assert.assertEquals("The Shawshank Redemption", dto.getOriginalTitle());
            Assert.assertEquals("en", dto.getOriginalLanguage());
            Assert.assertEquals("The Shawshank Redemption", dto.getTitle());
            Assert.assertEquals("/j9XKiZrVeViAixVRzCta7h1VU9W.jpg", dto.getBackdropPath());
            Assert.assertEquals(6949, dto.getVoteCount());
            Assert.assertFalse(dto.isVideo());
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    @Test
    public void testPagedMoviesJsonReadable() throws Exception {
        JsonReader reader = null;
        try {
            reader = new JsonReader(new StringReader(PAGED_MOVIES_JSON));
            final PagedMoviesDto dto = pagedMoviesReadable.readJson(reader);
            Assert.assertNotNull(dto);
            Assert.assertEquals(1, dto.getPage());
            Assert.assertEquals(1, dto.getTotalPages());
            Assert.assertEquals(1, dto.getTotalResults());
            Assert.assertEquals(1, dto.getResults().size());
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    private static final String VIDEO_ITEM_JSON = "{\n" +
            "      \"id\": \"58f4d4129251413d76023ecf\",\n" +
            "      \"iso_639_1\": \"en\",\n" +
            "      \"iso_3166_1\": \"US\",\n" +
            "      \"key\": \"Kf2kk8T3Z9A\",\n" +
            "      \"name\": \"I Liked Andy\",\n" +
            "      \"site\": \"YouTube\",\n" +
            "      \"size\": 1080,\n" +
            "      \"type\": \"Clip\"\n" +
            "    }";
    private static final String PAGED_VIDEOS_JSON = "{\"id\":278, \"results\":["
            + VIDEO_ITEM_JSON + "]}";

    @Test
    public void testVideoItemJsonReadable() throws Exception {
        JsonReader reader = null;
        try {
            reader = new JsonReader(new StringReader(VIDEO_ITEM_JSON));
            final VideoItemDto dto = videoItemReadable.readJson(reader);
            Assert.assertNotNull(dto);
            Assert.assertEquals("58f4d4129251413d76023ecf", dto.getId());
            Assert.assertEquals("en", dto.getIso6391());
            Assert.assertEquals("US", dto.getIso31661());
            Assert.assertEquals("Kf2kk8T3Z9A", dto.getKey());
            Assert.assertEquals("I Liked Andy", dto.getName());
            Assert.assertEquals("YouTube", dto.getSite());
            Assert.assertEquals(1080, dto.getSize());
            Assert.assertEquals("Clip", dto.getType());
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    @Test
    public void testVideosJsonReadable() throws Exception {
        JsonReader reader = null;
        try {
            reader = new JsonReader(new StringReader(PAGED_VIDEOS_JSON));
            final VideosDto dto = videosReadable.readJson(reader);
            Assert.assertNotNull(dto);
            Assert.assertEquals(278, dto.getId());
            Assert.assertEquals(1, dto.getResults().size());
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    private static final String REVIEW_ITEM_JSON = "{\n" +
            "      \"id\": \"5723a329c3a3682e720005db\",\n" +
            "      \"author\": \"elshaarawy\",\n" +
            "      \"content\": \"very good movie 9.5/10\",\n" +
            "      \"url\": \"https://www.themoviedb.org/review/5723a329c3a3682e720005db\"\n" +
            "    }";
    private static final String PAGED_REVIEWS_JSON = "{\"id\":278, \"results\":["
            + REVIEW_ITEM_JSON + "], \"page\":1, \"total_pages\":1, \"total_results\":1}";

    @Test
    public void testReviewItemJsonReadable() throws Exception {
        JsonReader reader = null;
        try {
            reader = new JsonReader(new StringReader(REVIEW_ITEM_JSON));
            final ReviewItemDto dto = reviewItemReadable.readJson(reader);
            Assert.assertNotNull(dto);
            Assert.assertEquals("5723a329c3a3682e720005db", dto.getId());
            Assert.assertEquals("elshaarawy", dto.getAuthor());
            Assert.assertEquals("very good movie 9.5/10", dto.getContent());
            Assert.assertEquals("https://www.themoviedb.org/review/5723a329c3a3682e720005db", dto.getUrl());
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    @Test
    public void testPagedReviewsJsonReadable() throws Exception {
        JsonReader reader = null;
        try {
            reader = new JsonReader(new StringReader(PAGED_REVIEWS_JSON));
            final PagedReviewsDto dto = pagedReviewsReadable.readJson(reader);
            Assert.assertNotNull(dto);
            Assert.assertEquals(278, dto.getId());
            Assert.assertEquals(1, dto.getResults().size());
            Assert.assertEquals(1, dto.getPage());
            Assert.assertEquals(1, dto.getTotalPages());
            Assert.assertEquals(1, dto.getTotalResults());
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    private static final String NEW_TOKEN_JSON = "{\n" +
            "  \"success\": true,\n" +
            "  \"expires_at\": \"2017-05-02 15:44:20 UTC\",\n" +
            "  \"request_token\": \"39d6c9605303fbeaa59ede1df0d3e483c2ca4701\"\n" +
            "}";

    @Test
    public void testNewTokenDtoReadable() throws Exception {
        JsonReader reader = null;
        try {
            reader = new JsonReader(new StringReader(NEW_TOKEN_JSON));
            final NewTokenDto dto = newTokenReadable.readJson(reader);
            Assert.assertNotNull(dto);
            Assert.assertTrue(dto.isSuccess());
            final Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dto.getExpiresAt().getTime());
            calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
            Assert.assertEquals(calendar.get(Calendar.YEAR), 2017);
            Assert.assertEquals(calendar.get(Calendar.MONTH), Calendar.MAY);
            Assert.assertEquals(calendar.get(Calendar.DAY_OF_MONTH), 2);
            Assert.assertEquals(calendar.get(Calendar.HOUR_OF_DAY), 15);
            Assert.assertEquals(calendar.get(Calendar.MINUTE), 44);
            Assert.assertEquals(calendar.get(Calendar.SECOND), 20);
            Assert.assertEquals(dto.getToken(), "39d6c9605303fbeaa59ede1df0d3e483c2ca4701");
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    private static final String NEW_SESSION_JSON = "{" +
            "\"success\":true," +
            "\"session_id\":\"jfvhgcvbgvcbvcnvcvn\"}";

    @Test
    public void testNewSessionDtoReadable() throws Exception {
        JsonReader reader = null;
        try {
            reader = new JsonReader(new StringReader(NEW_SESSION_JSON));
            final NewSessionDto dto = newSessionReadable.readJson(reader);
            Assert.assertNotNull(dto);
            Assert.assertTrue(dto.isSuccess());
            Assert.assertEquals(dto.getSessionId(), "jfvhgcvbgvcbvcnvcvn");
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    private static final String NEW_GUEST_SESSION_JSON = "{\n" +
            "  \"success\": true,\n" +
            "  \"guest_session_id\": \"73be8f09e448b0ec0580ec9354f92c5c\",\n" +
            "  \"expires_at\": \"2017-05-03 14:35:20 UTC\"\n" +
            "}";

    @Test
    public void testNewGuestSessionDtoReadable() throws Exception {
        JsonReader reader = null;
        try {
            reader = new JsonReader(new StringReader(NEW_GUEST_SESSION_JSON));
            final NewGuestSessionDto dto = newGuestSessionReadable.readJson(reader);
            Assert.assertNotNull(dto);
            Assert.assertTrue(dto.isSuccess());
            Assert.assertEquals(dto.getGuestSessionId(), "73be8f09e448b0ec0580ec9354f92c5c");
            final Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dto.getExpiresAt().getTime());
            calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
            Assert.assertEquals(calendar.get(Calendar.YEAR), 2017);
            Assert.assertEquals(calendar.get(Calendar.MONTH), Calendar.MAY);
            Assert.assertEquals(calendar.get(Calendar.DAY_OF_MONTH), 3);
            Assert.assertEquals(calendar.get(Calendar.HOUR_OF_DAY), 14);
            Assert.assertEquals(calendar.get(Calendar.MINUTE), 35);
            Assert.assertEquals(calendar.get(Calendar.SECOND), 20);
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }
}

package com.reactivespring.controller;

import com.reactivespring.domain.MovieInfo;
import com.reactivespring.domain.repository.MovieInfoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
@ImportAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class)
class MoviesInfoControllerTest {
    private static final String MOVIE_INFO_URL = "/v1/movieinfos";
    @Autowired
    private MovieInfoRepository movieInfoRepository;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp(){
        List<MovieInfo> of = new ArrayList<>();
        ArrayList<String> movieCast = new ArrayList<>();
        movieCast.add("Christian Bale");
        movieCast.add("Michael Cane");
        of.add(new MovieInfo(null, "BatMan Begins", 2005,
                movieCast, LocalDate.parse("2005-06-15")));
        of.add(new MovieInfo("abc", "BatMan second", 2005,
                movieCast, LocalDate.parse("2005-06-15")));
        movieInfoRepository.saveAll(of)
                .blockLast();
    }

    @AfterEach
    void tearDown(){
        movieInfoRepository.deleteAll();
    }

    @Test
    void addMovieInfo(){
        ArrayList<String> movieCast = new ArrayList<>();
        movieCast.add("Christian Bale");
        movieCast.add("Michael Cane");
        MovieInfo batMan_begins = new MovieInfo(null, "BatMan Begins", 2005,
                movieCast, LocalDate.parse("2005-06-15"));

        webTestClient
                .post()
                .uri(MOVIE_INFO_URL)
                .bodyValue(batMan_begins)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(MovieInfo.class)
                .consumeWith(movieInfoEntityExchangeResult ->
                {
                    MovieInfo responseBody = movieInfoEntityExchangeResult.getResponseBody();
                    assert responseBody != null;
                    assert responseBody.getMovieInfoId() != null;
                });
    }

    @Test
    void getAllMovieInfos(){

        webTestClient
                .get()
                .uri(MOVIE_INFO_URL)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(MovieInfo.class)
                .hasSize(2);
    }
}
package com.reactivespring.domain.repository;

import com.reactivespring.domain.MovieInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest//scan the mongodb related class and add into spring bean
@ActiveProfiles("test")
@ImportAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class)
class MovieInfoRepositoryIntegrationTest {
    @Autowired
    MovieInfoRepository movieInfoRepository;

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
        movieInfoRepository.deleteAll().block();
    }

    @Test
    void findAll(){
        Flux<MovieInfo> movieInfoFlux = movieInfoRepository.findAll().log();

        StepVerifier.create(movieInfoFlux)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void findById(){
        Mono<MovieInfo> abc = movieInfoRepository.findById("abc").log();

        StepVerifier.create(abc)
                .assertNext(movieInfo -> {
                    assertEquals("BatMan Begins", movieInfo.getName());
                })
                .verifyComplete();
    }

    @Test
    void deleteMovieInfo(){

        movieInfoRepository.deleteById("abc").block();
        Flux<MovieInfo> log = movieInfoRepository.findAll().log();

        StepVerifier.create(log)
                .expectNextCount(1)
                .verifyComplete();
    }
}
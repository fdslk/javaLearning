package com.reactivespring.domain.repository;

import com.reactivespring.domain.MovieInfo;
import com.sun.tools.javac.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDate;

//@EnableMongoRepositories(basePackageClasses = {MovieInfoRepository.class})
@DataMongoTest//scan the mongodb related class and add into spring bean
@ActiveProfiles("local")
@ImportAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class)
class MovieInfoRepositoryIntegrationTest {
    @Autowired
    MovieInfoRepository movieInfoRepository;

    @BeforeEach
    void setUp(){
        List<MovieInfo> of = List.of(new MovieInfo(null, "BatMan Begins", 2005,
                List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15")));
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
                .expectNextCount(1)
                .verifyComplete();
    }
}
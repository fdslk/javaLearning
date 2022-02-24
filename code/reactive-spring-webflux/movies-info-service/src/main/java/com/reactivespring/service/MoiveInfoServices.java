package com.reactivespring.service;

import com.reactivespring.domain.MovieInfo;
import com.reactivespring.domain.repository.MovieInfoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MoiveInfoServices {

    private MovieInfoRepository movieInfoRepository;

    public MoiveInfoServices(MovieInfoRepository movieInfoRepository) {
        this.movieInfoRepository = movieInfoRepository;
    }

    public Mono<MovieInfo> addMovieInfo(MovieInfo movieInfo) {
        return this.movieInfoRepository.save(movieInfo);
    }

    public Flux<MovieInfo> getAllMovieInfos() {
        return this.movieInfoRepository.findAll();
    }
}

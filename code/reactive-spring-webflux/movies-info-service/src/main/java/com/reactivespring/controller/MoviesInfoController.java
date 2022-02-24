package com.reactivespring.controller;

import com.reactivespring.domain.MovieInfo;
import com.reactivespring.service.MoiveInfoServices;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1")
public class MoviesInfoController {

    private MoiveInfoServices moiveInfoServices;

    public MoviesInfoController(MoiveInfoServices moiveInfoServices) {
        this.moiveInfoServices = moiveInfoServices;
    }

    @PostMapping("movieinfos")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovieInfo> addMovieInfo(@RequestBody MovieInfo movieInfo){
        return moiveInfoServices.addMovieInfo(movieInfo);
    }

    @GetMapping("movieinfos")
    public Flux<MovieInfo> getAllMovieInfos(@RequestBody MovieInfo movieInfo){
        return moiveInfoServices.getAllMovieInfos();
    }
}

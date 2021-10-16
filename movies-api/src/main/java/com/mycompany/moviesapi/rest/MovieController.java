package com.mycompany.moviesapi.rest;

import com.mycompany.moviesapi.mapper.MovieMapper;
import com.mycompany.moviesapi.model.Movie;
import com.mycompany.moviesapi.rest.dto.CreateMovieRequest;
import com.mycompany.moviesapi.rest.dto.MovieResponse;
import com.mycompany.moviesapi.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static net.logstash.logback.argument.StructuredArguments.v;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;
    private final MovieMapper movieMapper;

    @GetMapping
    public List<MovieResponse> getMovies() {
        log.info("Get all movies request");
        return movieService.getMovies().stream()
                .map(movieMapper::toMovieResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{imdb}")
    public MovieResponse getMovie(@PathVariable("imdb") String imdb) {
        log.info("Get movie request", v("imdb", imdb));
        if (imdb.equals("111")) {
            log.error("It is know there is a bug with this movie", v("imdb", imdb));
            throw new NullPointerException("It is know there is a bug with this movie");
        }
        Movie movie = movieService.validateAndGetMovie(imdb);
        return movieMapper.toMovieResponse(movie);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public MovieResponse createMovie(@Valid @RequestBody CreateMovieRequest createMovieRequest) {
        log.info("Movie create request", v("imdb", createMovieRequest.getImdb()));
        Movie movie = movieMapper.toMovie(createMovieRequest);
        movie = movieService.createMovie(movie);
        return movieMapper.toMovieResponse(movie);
    }

    @DeleteMapping("/{imdb}")
    public String deleteMovie(@PathVariable("imdb") String imdb) {
        log.info("Movie delete request", v("imdb", imdb));
        Movie movie = movieService.validateAndGetMovie(imdb);
        movieService.deleteMovie(movie);
        return movie.getImdb();
    }
}

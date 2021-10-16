package com.mycompany.moviesapi.service;

import com.mycompany.moviesapi.exception.MovieNotFoundException;
import com.mycompany.moviesapi.model.Movie;
import com.mycompany.moviesapi.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public Movie validateAndGetMovie(String imdb) {
        log.info("Retrieving movie from db by imdb={}", imdb);
        return movieRepository.findById(imdb).orElseThrow(() -> new MovieNotFoundException(imdb));
    }

    @Override
    public List<Movie> getMovies() {
        List<Movie> movies = movieRepository.findAll();
        log.info("Retrieved {} movies from db", movies.size());
        return movies;
    }

    @Override
    public Movie createMovie(Movie movie) {
        Movie created = movieRepository.save(movie);
        log.info("{} was saved to db", movie);
        return created;
    }

    @Override
    public void deleteMovie(Movie movie) {
        log.info("Deleting movie from db by imdb={}", movie.getImdb());
        movieRepository.delete(movie);
    }
}

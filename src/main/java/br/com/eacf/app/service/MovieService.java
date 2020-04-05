package br.com.eacf.app.service;

import br.com.eacf.app.entity.Movie;
import br.com.eacf.app.exception.ResourceNotFoundException;
import br.com.eacf.app.jpa.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    public Movie findById(Long id) throws ResourceNotFoundException {
        return this.movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Movie save(Movie movie) {
        return this.movieRepository.save(movie);
    }

    public void deleteById(Long id) {
        this.movieRepository.deleteById(id);
    }

    public Iterable<Movie> findAll(){
        return movieRepository.findAll();
    }

    public Iterable<Movie> findByWinner(boolean winner){
        return movieRepository.findByWinner(winner);
    }
}

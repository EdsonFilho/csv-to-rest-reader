package br.com.eacf.app.service;

import br.com.eacf.app.entity.Movie;
import br.com.eacf.app.entity.Producer;
import br.com.eacf.app.entity.Studio;
import br.com.eacf.app.exception.ResourceNotFoundException;
import br.com.eacf.app.jpa.MovieRepository;
import br.com.eacf.app.jpa.ProducerRepository;
import br.com.eacf.app.jpa.StudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    private MovieRepository movieRepository;
    private StudioRepository studioRepository;
    private ProducerRepository producerRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository, StudioRepository studioRepository, ProducerRepository producerRepository){
        this.movieRepository = movieRepository;
        this.studioRepository = studioRepository;
        this.producerRepository = producerRepository;
    }

    public Movie findById(Long id) throws ResourceNotFoundException {
        return this.movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Movie save(Movie movie) {
        List<Studio> persistedStudios = new ArrayList<>();
        List<Producer> persistedProducers = new ArrayList<>();
        for(Producer producer : movie.getProducers()){
            System.out.println(producer.toString());
            List<Producer> ls = this.producerRepository.findByName(producer.getName());
            if(ls.isEmpty()){
                persistedProducers.add(this.producerRepository.save(producer));
            } else {
                persistedProducers.addAll(ls);
            }
        }
        movie.setProducers(persistedProducers);
        for(Studio studio : movie.getStudios()){
            System.out.println(studio.toString());
            List<Studio> ls = this.studioRepository.findByName(studio.getName());
            if(ls.isEmpty()){
                persistedStudios.add(this.studioRepository.save(studio));
            } else {
                persistedStudios.addAll(ls);
            }
        }
        movie.setStudios(persistedStudios);
        return this.movieRepository.save(movie);
    }

    public void deleteById(Long id) {
        this.movieRepository.deleteById(id);
    }

    public Iterable<Movie> findAll(){
        return movieRepository.findAll();
    }
}

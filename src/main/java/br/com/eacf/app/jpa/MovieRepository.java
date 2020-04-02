package br.com.eacf.app.jpa;


import br.com.eacf.app.entity.Movie;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MovieRepository extends CrudRepository<Movie, Long> {

    List<Movie> findByTitle(String title);

}

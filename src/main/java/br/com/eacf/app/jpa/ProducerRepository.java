package br.com.eacf.app.jpa;

import br.com.eacf.app.entity.Movie;
import br.com.eacf.app.entity.Producer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProducerRepository extends CrudRepository<Producer, Long> {

    List<Producer> findByName(String title);
}

package br.com.eacf.app.jpa;

import br.com.eacf.app.entity.Studio;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudioRepository extends CrudRepository<Studio, Long> {

    List<Studio> findByName(String title);
}

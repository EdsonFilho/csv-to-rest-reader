package br.com.eacf.app.endpoint;

import br.com.eacf.app.entity.Movie;
import br.com.eacf.app.entity.ProducerInterval;
import br.com.eacf.app.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("producer")
public class ProducerController {

    private MovieService service;

    @Autowired
    public ProducerController(MovieService service){
        this.service = service;
    }

    @GetMapping("/intervals/")
    public ResponseEntity<Iterable<ProducerInterval>> findAll(){
        // TODO
        return ResponseEntity.ok(null);
    }

}

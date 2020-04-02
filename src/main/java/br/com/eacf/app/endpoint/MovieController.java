package br.com.eacf.app.endpoint;

import br.com.eacf.app.service.MovieService;
import br.com.eacf.app.entity.Movie;
import br.com.eacf.app.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("movie")
public class MovieController {

    private MovieService service;


    @Autowired
    public MovieController(MovieService service){
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<Iterable<Movie>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> findById(@PathVariable Long id){
        try {
            Movie movie = service.findById(id);
            return ResponseEntity.ok(movie);  // return 200, with json body
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }

    @PostMapping("/")
    public ResponseEntity<Movie> findById(@RequestBody Movie movie){
        return ResponseEntity.ok(service.save(movie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> delete(@PathVariable Long id) {
        try {
            Movie movie = service.findById(id);
            service.deleteById(id);
            return ResponseEntity.ok(movie);  // return 200, with json body
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }
}

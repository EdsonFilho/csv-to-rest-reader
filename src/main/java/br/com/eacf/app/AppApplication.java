package br.com.eacf.app;

import br.com.eacf.app.csv.Reader;
import br.com.eacf.app.entity.Movie;
import br.com.eacf.app.entity.Producer;
import br.com.eacf.app.entity.Studio;
import br.com.eacf.app.jpa.MovieRepository;
import br.com.eacf.app.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class AppApplication {

	private static final Logger log = LoggerFactory.getLogger(AppApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}


	@Bean
	public CommandLineRunner demo(MovieService service, Reader reader) {
//	public void demo(MovieService service, Reader reader) {
		return (args) -> {
			// Reads all movies from csv file
			List<Movie> movies = reader.readCSV();
			// Persists all movies
			movies.stream().forEach(service::save);
		};
	}

//	@Bean
//	public CommandLineRunner demo2(MovieRepository repository) {
	public void demo2(MovieRepository repository) {
//		return (args) -> {
		// save a few customers
		repository.save(new Movie(1l, "2000", "Filme A", Arrays.asList(new Studio("Jerry A"), new Studio("Tom A")), Arrays.asList(new Producer("Jerry A"), new Producer("Tom A")), true));
		repository.save(new Movie(2l, "2001", "Filme B", Arrays.asList(new Studio("Jerry B"), new Studio("Tom B")), Arrays.asList(new Producer("Jerry B"), new Producer("Tom B")), false));
		repository.save(new Movie(3l, "2002", "Filme C", Arrays.asList(new Studio("Jerry A"), new Studio("Tom B")), Arrays.asList(new Producer("Jerry C"), new Producer("Tom C")), false));
		repository.save(new Movie(4l, "2003", "Filme D", Arrays.asList(new Studio("Jerry D"), new Studio("Tom D")), Arrays.asList(new Producer("Jerry D"), new Producer("Tom D")), true));


		// fetch all customers
		log.info("Movies found with findAll():");
		log.info("-------------------------------");
		for (Movie customer : repository.findAll()) {
			log.info(customer.toString());
		}
		log.info("");

		// fetch an individual customer by ID
		Movie movie = repository.findById(1L).get();
		log.info("Movies found with findById(1L):");
		log.info("--------------------------------");
		log.info(movie.toString());
		log.info("");

		// fetch customers by last name
		log.info("Movies found with findByTitle('Filme E'):");
		log.info("--------------------------------------------");
		repository.findByTitle("Filme E").forEach(bauer -> {
			log.info(bauer.toString());
		});
		// for (Customer bauer : repository.findByLastName("Bauer")) {
		//  log.info(bauer.toString());
		// }
		log.info("");
		//System.exit(0);
//		};
	}
}

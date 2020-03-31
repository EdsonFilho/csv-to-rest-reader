package br.com.eacf.app;

import br.com.eacf.app.entity.Movie;
import br.com.eacf.app.jpa.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AppApplication {

	private static final Logger log = LoggerFactory.getLogger(AppApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}


	@Bean
	public CommandLineRunner demo(MovieRepository repository) {
		return (args) -> {
			// save a few customers
			repository.save(new Movie(1l, "2000", "Filme A", "Studio A", "Producer A", "yes"));
			repository.save(new Movie(2l, "2001", "Filme B", "Studio B", "Producer B", ""));
			repository.save(new Movie(3l, "2002", "Filme C", "Studio C", "Producer C", ""));
			repository.save(new Movie(4l, "2003", "Filme D", "Studio D", "Producer D", "yes"));
			repository.save(new Movie(5l, "2004", "Filme E", "Studio E", "Producer E", "no"));

			// fetch all customers
			log.info("Movies found with findAll():");
			log.info("-------------------------------");
			for (Movie customer : repository.findAll()) {
				log.info(customer.toString());
			}
			log.info("");

			// fetch an individual customer by ID
			Movie movie = repository.findById(1L);
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
			System.exit(0);
		};
	}
}

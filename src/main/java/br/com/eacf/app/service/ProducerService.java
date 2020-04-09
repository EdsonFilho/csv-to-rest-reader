package br.com.eacf.app.service;

import br.com.eacf.app.entity.Movie;
import br.com.eacf.app.entity.ProducerInterval;
import br.com.eacf.app.entity.ProducerWin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProducerService {

    private MovieService service;

    @Autowired
    public ProducerService(MovieService service){
        this.service = service;
    }

    public ProducerInterval getProducerIntervals(){
        ProducerInterval interval = new ProducerInterval();

        // Searches all ProducersWin
        List<ProducerWin> wins = this.getProducerWins();

        // Sort by interval
        wins.sort(Comparator.comparing(ProducerWin::getInterval));

        // Defines smallest and biggest intervals
        Integer minInterval = wins.get(0).getInterval();
        Integer maxInterval = wins.get(wins.size()-1).getInterval();

        //Filters wins list by smallest interval
        interval.setMin(this.filterWinsByInterval(wins, minInterval));

        //Filters wins list by biggest interval
        interval.setMax(this.filterWinsByInterval(wins, maxInterval));

        return interval;
    }

    private List<ProducerWin> filterWinsByInterval(List<ProducerWin> wins, Integer interval){
        return wins.stream()
                .filter(win -> win.getInterval().equals(interval))
                .collect(Collectors.toList());
    }


    /**
     * Filter the Map, keeping only the producers that contains more than two movies
     *
     * @param producers
     * @return
     */
    private Map<String, List<Movie>> getProducersWithMoreThanTwoMovies(Map<String, List<Movie>> producers){
        return producers.entrySet().stream()
                .filter(map -> map.getValue().size() > 1)
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
    }

    /**
     * Return a Map with the producer name as key and a list of winner movie as value
     *
     * @return
     */
    private Map<String, List<Movie>> getProducersWithWinnerMovies(){
        Map<String, List<Movie>> producers = new HashMap<>();
        Iterable<Movie> movies = this.service.findByWinner(true);

        movies.forEach((movie) -> {
            movie.getProducers().forEach((producer) -> {
                if(!producers.containsKey(producer)){
                    producers.put(producer, new ArrayList<>());
                }
                producers.get(producer).add(movie);
            });
        });
        return producers;
    }

    /**
     * Return a List of Producers that have more than two winner movies
     *
     * @return
     */
    private List<ProducerWin> getProducerWins(){
        List<ProducerWin> wins = new ArrayList<>();
        Map<String, List<Movie>> producers = this.getProducersWithMoreThanTwoMovies(this.getProducersWithWinnerMovies());
        producers.forEach((producer, movies) -> {
            movies.sort(Comparator.comparing(Movie::getYear));
            wins.addAll(this.winSplitter(movies, producer));
        });
        return wins;
    }

    /**
     * Receives a Movie List and Splits it in a list of ProducerWin
     * The for generates pairs of movies, like when receiving a list contraining [A, B, C] it will
     *
     * @param movies
     * @param producer
     * @return
     */
    private List<ProducerWin> winSplitter(List<Movie> movies, String producer){
        List<ProducerWin> ls = new ArrayList<>();
        movies.sort(Comparator.comparing(Movie::getYear));

        // Creates a pair of movies to be compared
        for(int i =0; i<movies.size()-1; i++){
            Movie previous = movies.get(i);
            Movie following = movies.get(i+1);
            ls.add(this.getProducerWin(previous, following, producer));
        }
        return ls;
    }

    /**
     * Receives two movies and a producer to create a ProducerWin
     *
     * @param previous
     * @param following
     * @param producer
     * @return
     */
    private ProducerWin getProducerWin(Movie previous, Movie following, String producer){
        ProducerWin pi = new ProducerWin();
        pi.setProducer(producer);
        pi.setPreviousWin(previous.getYear());
        pi.setFollowingWin(following.getYear());
        pi.setInterval(following.getYear() - previous.getYear());
        return pi;
    }
}

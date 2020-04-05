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
        List<ProducerWin> wins = this.getProducerWins();

        wins.sort(Comparator.comparing(ProducerWin::getInterval));

        Integer minInterval = wins.get(0).getInterval();
        Integer maxInterval = wins.get(wins.size()-1).getInterval();

        List<ProducerWin> min = wins.stream()
                .filter(win -> win.getInterval().equals(minInterval))
                .collect(Collectors.toList());

        List<ProducerWin> max = wins.stream()
                .filter(win -> win.getInterval().equals(maxInterval))
                .collect(Collectors.toList());

        interval.setMin(min);
        interval.setMax(max);

        return interval;
    }

    private Map<String, List<Movie>> getProducersWithMoreThanTwoMovies(Map<String, List<Movie>> producers){
        return producers.entrySet().stream()
                .filter(map -> map.getValue().size() > 1)
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
    }

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

        Map<String, List<Movie>> sorted = producers
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (e1, e2) -> e2, LinkedHashMap::new));

        return producers;
    }

    private List<ProducerWin> getProducerWins(){
        List<ProducerWin> wins = new ArrayList<>();
        Map<String, List<Movie>> producers = this.getProducersWithMoreThanTwoMovies(this.getProducersWithWinnerMovies());
        producers.forEach((producer, movies) -> {
            ProducerWin pi = this.getProducerWin(movies);
            pi.setProducer(producer);
            wins.add(pi);
        });
        return wins;
    }

    private ProducerWin getProducerWin(List<Movie> movies){
        ProducerWin pi = new ProducerWin();
        movies.forEach(Movie::toString);
        movies.sort(Comparator.comparing(Movie::getYear));

        Movie previous = movies.get(0);
        Movie following = movies.get(movies.size()-1);

        pi.setPreviousWin(previous.getYear());
        pi.setFollowingWin(following.getYear());
        pi.setInterval(following.getYear() - previous.getYear());

        return pi;
    }
}

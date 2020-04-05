package br.com.eacf.app.csv;

import br.com.eacf.app.entity.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class Reader {

    private static final Logger log = LoggerFactory.getLogger(Reader.class);

    public List<Movie> readCSV(){
        List<Movie> movies = new ArrayList<>();
        File csvFile = new File("movielist.csv");
        try {
            int recordSize = 5;
            Files.readAllLines(csvFile.toPath(), StandardCharsets.UTF_8).stream().skip(1).forEach(line -> {
                List<String> record = new ArrayList<>(recordSize);
                record.addAll(Arrays.asList(line.split(";")));

                while(record.size() < recordSize){ record.add(""); } //workaround 4 < 5 to avoid indexoutofbounds...

                movies.add(this.parseMovie(record));
            });
        } catch (IOException e) {
            log.error("Reading CSV Error!", e);
        }

        log.info("movielist.csv read successfully");

        return movies;
    }

    // Method responsible for create the Movie object with the data read from the csv file
    private Movie parseMovie(List<String> record){
        return new Movie(null, Integer.parseInt(record.get(0)), record.get(1), this.parseString(record.get(2)), this.parseString(record.get(3)), this.parseBooleanAttribute(record.get(4)));
    }

    // Method responsible for removing the , and and word and creating a list with the values
    private List<String> parseString(String record){
        List<String> ls = new ArrayList<>();
        String[] values = record.split(", ");
        Arrays.stream(values).forEach((value) -> {
            // Do a new sprit if the string containd the word "and"
            if(value.contains(" and ")){
                String[] subValues = value.split(" and ");
                ls.add(subValues[0]);
                ls.add(subValues[1]);
            } else {
                ls.add(value);
            }
        });
        return ls;
    }

    private boolean parseBooleanAttribute(String record){
        return  "yes".equalsIgnoreCase(record) ? true : false;
    }

}
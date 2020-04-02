package br.com.eacf.app.csv;

import br.com.eacf.app.entity.Movie;
import br.com.eacf.app.entity.Producer;
import br.com.eacf.app.entity.Studio;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class Reader {

    private static final Logger log = LoggerFactory.getLogger(Reader.class);

    public List<Movie> readCSV(){
        BufferedReader fileReader = null;
        CSVReader csvReader = null;
        List<Movie> movies = new ArrayList<>();

        try {
            log.info("Locating file movielist.csv");
            fileReader = new BufferedReader(new FileReader("movielist.csv"));
            CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
            csvReader = new CSVReaderBuilder(fileReader).withCSVParser(parser).build();
            String[] record;

            log.info("Reading file movielist.csv");
            csvReader.readNext(); // skip Header
            while ((record = csvReader.readNext()) != null) {
                movies.add(this.parseMovie(record));
            }
            log.info("movielist.csv read successfully");
            csvReader.close();

        } catch (Exception e) {
            log.error("Reading CSV Error!", e);
            //Exiting application when unable to read csv file
            System.exit(1);
        } finally {
            try {
                fileReader.close();
                csvReader.close();
            } catch (IOException e) {
                log.error("Error closing fileReader/csvParser!", e);
            }
        }
        log.info("Reading file movielist.csv");
        return movies;
    }

    // Methos responsible for create the Movie object with the data read from the csv file
    private Movie parseMovie(String[] record){
        return new Movie(null, record[0], record[1], this.parseStudios(record[2]), this.parseProducers(record[3]), this.parseBooleanAttribute(record[4]));
    }

    // Method responsible for removing the , and and word and creating a list with the values
    private List<Producer> parseProducers(String record){
        List<Producer> ls = new ArrayList<>();
        String[] values = record.split(", ");
        Arrays.stream(values).forEach((value) -> {
            // Do a new sprit if the string containd the word "and"
            if(value.contains(" and ")){
                String[] subValues = value.split(" and ");
                ls.add(new Producer(subValues[0]));
                ls.add(new Producer(subValues[1]));
            } else {
                ls.add(new Producer(value));
            }
        });
        return ls;
    }

    // Method responsible for removing the , and and word and creating a list with the values
    private List<Studio> parseStudios(String record){
        List<Studio> ls = new ArrayList<>();
        String[] values = record.split(", ");
        Arrays.stream(values).forEach((value) -> {
            // Do a new sprit if the string containd the word "and"
            if(value.contains(" and ")){
                String[] subValues = value.split(" and ");
                ls.add(new Studio(subValues[0]));
                ls.add(new Studio(subValues[1]));
            } else {
                ls.add(new Studio(value));
            }
        });
        return ls;
    }

    private boolean parseBooleanAttribute(String record){
        return  "yes".equalsIgnoreCase(record) ? true : false;
    }

}

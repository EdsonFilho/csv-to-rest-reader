package br.com.eacf.app.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Component
public class Reader {

    public void readCSV(){
        BufferedReader fileReader = null;
        CSVReader csvReader = null;

        try {
            File f = new File("customer.csv");

            System.out.println(f.getPath());

            fileReader = new BufferedReader(new FileReader(f));
            csvReader = new CSVReader(fileReader);
            String[] record;

            System.out.println("--- Read line by line ---");
            csvReader.readNext(); // skip Header
            while ((record = csvReader.readNext()) != null) {
                System.out.println(record[0] + " | " + record[1] + " | " + record[2] + " | " + record[3]);
            }

            csvReader.close();

            // -------------------------------------------
            System.out.println("\n--- Read all at once ---");

            fileReader = new BufferedReader(new FileReader("customer.csv"));
            csvReader = new CSVReaderBuilder(fileReader).withSkipLines(1).build();
            List<String[]> records = csvReader.readAll();

            for (String[] _record : records) {
                System.out.println(_record[0] + " | " + _record[1] + " | " + _record[2] + " | " + _record[3]);
            }
        } catch (Exception e) {
            System.out.println("Reading CSV Error!");
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
                csvReader.close();
            } catch (IOException e) {
                System.out.println("Closing fileReader/csvParser Error!");
                e.printStackTrace();
            }
        }

    }
}

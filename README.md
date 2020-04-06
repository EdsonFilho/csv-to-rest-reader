# csv-to-rest-reader
This application reads data from a CSV file, inserts the data on a H2 database and make this data available through a Rest API.

## Cloning
To clone the application run the following command:

<code>git clone https://github.com/EdsonFilho/csv-to-rest-reader.git</code>

## Testing
To run the integration tests run the following command:

<code>mvn test</code> 

## Runing
To build the application run the following command:

<code>mvn clean package</code>

After running this command, a file called csv-to-rest-reader-0.0.1-SNAPSHOT.jar will be created on the /target directory.

To run the application run the following command:

<code>java -jar target/csv-to-rest-reader-0.0.1-SNAPSHOT.jar</code>

The application will read the file CSV located on the root directory called movielist.csv, and will insert this data on a H2 database.
This data will become available on the address http://localhost:8080 through a REST Api.

## Resources
Movie endPoint:
 
<code>http://localhost:8080/movie/</code>

Producers endPoint:
 
<code>http://localhost:8080/producers/</code>

This endpoint accepts only Get Requests. 



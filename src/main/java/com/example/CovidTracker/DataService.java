package com.example.CovidTracker;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class DataService {
    public static String Confirm_Case_URL="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    /**
     *GET request that prints the response body as a String
     *the string contains a CSV file
     * prints the state of the affected
     * scheduled to run every hour
     */
    @PostConstruct
    @Scheduled(cron = "**1***")
    public void reqData() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Confirm_Case_URL))
                .build();
        HttpResponse<String> httpResponse=client.send(request, HttpResponse.BodyHandlers.ofString());
        //System.out.println(httpResponse.body());
        StringReader csvReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records;
        records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvReader);
        for (CSVRecord record : records) {
            String state = record.get("Province/State");
            System.out.println(state);
        }

    }
}

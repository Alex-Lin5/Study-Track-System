package com.example.Spring.Commits;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import com.example.Spring.StudyTrackSystemApplication;
import com.example.Spring.entity.Commit;
import com.example.Spring.entity.Track;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CreateCommitTest {
    ApplicationContext app;
    HttpClient webClient;
    ObjectMapper objectMapper;
    String host = "http://localhost:8080";

    @BeforeEach
    public void setup() throws InterruptedException{
        webClient = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
        String[] args = new String[]{};
        app = SpringApplication.run(StudyTrackSystemApplication.class, args);
        Thread.sleep(500);
    }
    @AfterEach
    public void teardown() throws InterruptedException{
        Thread.sleep(500);
        SpringApplication.exit(app);
    }
    @Test
    public void createCommitWithNullTrackFrom() throws IOException, InterruptedException {
        String json = "{\"date_posted\": 1769947792, \"start_hour\": 1669947798, \"end_hour\": 1669947799}";
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(host + "/commits"))
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .header("Content-Type", "application/json")
            .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(200, status, "Expected status code 200, actual code is " + status);
        Commit expected = new Commit(1, 1769947792L, 1669947798L, 1669947799L);
        Commit result = objectMapper.readValue(response.body().toString(), new TypeReference<Commit>(){});
        Assertions.assertEquals(expected, result, "Expected="+expected + ", Result="+result);
    }
    @Test
    public void createCommitWithInvalidTrackFrom() throws IOException, InterruptedException {
        String json = "{\"date_posted\": 1769947792, \"start_hour\": 1669947798, \"end_hour\": 1669947799, \"from\": 200}";
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(host + "/commits"))
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .header("Content-Type", "application/json")
            .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(400, status, "Expected status code 200, actual code is " + status);
        String expected = "";
        String result = response.body();
        Assertions.assertEquals(expected, result, "Expected="+expected + ", Result="+result);
    }
    @Test
    public void createCommitWithValidTrackFrom() throws IOException, InterruptedException {
        String json = "{\"date_posted\": 1769947792, \"start_hour\": 1669947798, \"end_hour\": 1669947799, \"from\": 10}";
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(host + "/commits"))
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .header("Content-Type", "application/json")
            .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(200, status, "Expected status code 200, actual code is " + status);
        Track from = new Track(10);
        Commit expected = new Commit(1, 1769947792L, 1669947798L, 1669947799L, from);
        Commit result = objectMapper.readValue(response.body().toString(), new TypeReference<Commit>(){});
        Assertions.assertEquals(expected, result, "Expected="+expected + ", Result="+result);
    }
   
}

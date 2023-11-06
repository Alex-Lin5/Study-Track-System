package com.example.Spring.Commits;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import com.example.Spring.StudyTrackSystemApplication;
import com.example.Spring.entity.Commit;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RemoveCommitByIdTest {
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
    public void removeCommitSuccess() throws IOException, InterruptedException {
        Integer commit_id = 50;
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(host + "/commits/"+ commit_id))
            .DELETE()
            .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(200, status, "Expected status code 200, actual code is " + status);
        Commit expected = new Commit(50, 1669947792L, 1669947798L, 1669947799L);
        Commit result = objectMapper.readValue(response.body().toString(), new TypeReference<Commit>(){});
        Assertions.assertEquals(expected, result, "Expected="+expected + ", Result="+result);
    }
    @Test
    public void removeCommitWithNonexistedId() throws IOException, InterruptedException {
        Integer commit_id = 500;
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(host + "/commits/"+ commit_id))
            .DELETE()
            .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(400, status, "Expected status code 400, actual code is " + status);
        String expected = "";
        String result = response.body();
        Assertions.assertEquals(expected, result, "Expected="+expected + ", Result="+result);
    }
    // @Test
    // public void removeCommitWithRandomChar() throws IOException, InterruptedException {
    //     HttpRequest request = HttpRequest.newBuilder()
    //         .uri(URI.create(host + "/commits/k"))
    //         .DELETE()
    //         .build();
    //     HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
    //     int status = response.statusCode();
    //     Assertions.assertEquals(400, status, "Expected status code 400, actual code is " + status);
    //     String expected = "";
    //     String result = response.body();
    //     Assertions.assertEquals(expected, result, "Expected="+expected + ", Result="+result);
    // }
    // @Test
    // public void removeCommitWithoutId() throws IOException, InterruptedException {
    //     HttpRequest request = HttpRequest.newBuilder()
    //         .uri(URI.create(host + "/commits"))
    //         .DELETE()
    //         .build();
    //     HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
    //     int status = response.statusCode();
    //     Assertions.assertEquals(400, status, "Expected status code 400, actual code is " + status);
    //     String expected = "";
    //     String result = response.body();
    //     Assertions.assertEquals(expected, result, "Expected="+expected + ", Result="+result);
    // }
    
}

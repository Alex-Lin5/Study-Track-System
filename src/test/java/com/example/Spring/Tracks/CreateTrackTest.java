package com.example.Spring.Tracks;

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
import com.example.Spring.entity.Material;
import com.example.Spring.entity.Track;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CreateTrackTest {
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
    public void postTrackSuccessful() throws IOException, InterruptedException{
        String json = "{\"material_from\": 20, \"progress\": 10}";
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(host + "/tracks"))
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .header("Content-Type", "application/json")
            .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(200, status, "Expected status code 200, actual code is " + status);
        Track result = objectMapper.readValue(response.body().toString(), new TypeReference<Track>(){});
        Material material = new Material(20, "leetcode");
        Track expected = new Track(1, material, 10);
        Assertions.assertEquals(expected, result, "Expected="+expected + ", Result="+result);
    }
    @Test
    public void postTrackWithEmptyMaterial() throws IOException, InterruptedException{
        String json = "{\"progress\": 10}";
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(host + "/tracks"))
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .header("Content-Type", "application/json")
            .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(400, status, "Expected status code 400, actual code is " + status);
        String result = response.body();
        String expected = "";
        Assertions.assertEquals(expected, result, "Expected="+expected + ", Result="+result);
    }
    @Test
    public void postTrackWithInvalidMaterial() throws IOException, InterruptedException{
        String json = "{\"material_from\": 200, \"progress\": 10}";
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(host + "/tracks"))
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .header("Content-Type", "application/json")
            .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(400, status, "Expected status code 400, actual code is " + status);
        String result = response.body();
        String expected = "";
        Assertions.assertEquals(expected, result, "Expected="+expected + ", Result="+result);
    }
    @Test
    public void postTrackWithExistedTrack() throws IOException, InterruptedException{
        String json = "{\"material_from\": 10, \"progress\": 10}";
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(host + "/tracks"))
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .header("Content-Type", "application/json")
            .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(400, status, "Expected status code 400, actual code is " + status);
        String result = response.body();
        String expected = "";
        Assertions.assertEquals(expected, result, "Expected="+expected + ", Result="+result);
    }
    
    
}

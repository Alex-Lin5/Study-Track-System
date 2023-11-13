package com.example.Spring.Materials;

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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ModifyMaterialTest {
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
    public void putMaterialSuccessful() throws Exception {
        String json = "{\"material_id\":30, \"name\":\"gitt\", \"description\":\"version control\", \"note\":\"learned basic\"}";
        Material expected = new Material(30, "gitt", "version control", "undefined", "learned basic");
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(host + "/materials"))
            .PUT(HttpRequest.BodyPublishers.ofString(json))
            .header("Content-Type", "application/json")
            .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(200, status, "Expected status code 200, actual code is " + status);
        Material result = objectMapper.readValue(response.body().toString(), new TypeReference<Material>(){});
        Assertions.assertEquals(expected, result, "Expected="+expected + ", Result="+result);
    }   
    @Test
    public void putMaterialWithInvalidId() throws IOException, InterruptedException {
        String json = "{\"material_id\":300, \"name\":\"git\", \"description\":\"version control\", \"note\":\"learned basic\"}";
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(host + "/materials"))
            .PUT(HttpRequest.BodyPublishers.ofString(json))
            .header("Content-Type", "application/json")
            .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(400, status, "Expected status code 400, actual code is " + status);
        String expected = "";
        String result = response.body();
        Assertions.assertEquals(expected, result, "Expected="+expected + ", Result="+result);
    }   
    @Test
    public void putMaterialWithConflictName() throws IOException, InterruptedException {
        String json = "{\"material_id\":30, \"name\":\"spring\", \"description\":\"version control\", \"note\":\"learned basic\"}";
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(host + "/materials"))
            .PUT(HttpRequest.BodyPublishers.ofString(json))
            .header("Content-Type", "application/json")
            .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(400, status, "Expected status code 400, actual code is " + status);
        String expected = "";
        String result = response.body();
        Assertions.assertEquals(expected, result, "Expected="+expected + ", Result="+result);
    }   
    
}

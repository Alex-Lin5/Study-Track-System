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
// import org.springframework.http.HttpStatus;

import com.example.Spring.StudyTrackSystemApplication;
import com.example.Spring.entity.Material;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CreateMaterialTest {
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
    public void postMaterialSuccessful() throws IOException, InterruptedException {
        String json = "{\"material_id\":40, \"name\":\"maven\", \"description\":\"project manager\"}";
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(host + "/materials"))
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .header("Content-Type", "application/json")
            .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(200, status, "Expected status code 200, actual code is " + status);
        Material expected = new Material(1, "maven", "project manager");
        Material result = objectMapper.readValue(response.body().toString(), new TypeReference<Material>(){});
        Assertions.assertEquals(expected, result, "Expected="+expected + ", Result="+result);
    }   
    // @Test
    // public void PostMaterialWithDuplicateId() 
    // throws IOException, InterruptedException {
    //     String json = "{\"material_id\":30, \"name\":\"maven\", \"description\":\"project manager\"}";
    //     HttpRequest request = HttpRequest.newBuilder()
    //         .uri(URI.create(host + "/materials"))
    //         .POST(HttpRequest.BodyPublishers.ofString(json))
    //         .header("Content-Type", "application/json")
    //         .build();
    //     HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
    //     int status = response.statusCode();
    //     Assertions.assertEquals(400, status, "Expected status code 400, actual code is " + status);
    //     String expected = "";
    //     String result = response.body().toString();
    //     Assertions.assertEquals(expected, result, "Expected="+expected + ", Result="+result);
    // }   
    @Test
    public void PostMaterialWithDuplicateName() 
    throws IOException, InterruptedException {
        String json = "{\"material_id\":40, \"name\":\"git\", \"description\":\"project manager\"}";
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(host + "/materials"))
            .POST(HttpRequest.BodyPublishers.ofString(json))
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

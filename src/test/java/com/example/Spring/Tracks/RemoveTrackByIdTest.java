package com.example.Spring.Tracks;

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
import com.example.Spring.entity.Material;
import com.example.Spring.entity.Track;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RemoveTrackByIdTest {
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
    public void removeTrackSuccess() throws IOException, InterruptedException {
        Integer track_id = 10;
        HttpRequest deleteTrack = HttpRequest.newBuilder()
            .uri(URI.create(host + "/tracks/"+ track_id))
            .DELETE()
            .build();
        HttpResponse<String> trackRes = webClient.send(deleteTrack, HttpResponse.BodyHandlers.ofString());
        int status = trackRes.statusCode();
        Assertions.assertEquals(200, status, "Expected status code 200, actual code is " + status);
        Material material = new Material(30, "git", "version control");
        Track expTrack = new Track(10, material, 5);
        Track resTrack = objectMapper.readValue(trackRes.body().toString(), new TypeReference<Track>(){});
        Assertions.assertEquals(expTrack, resTrack, "Expected="+expTrack + ", Result="+resTrack);

        HttpRequest deleteCommits = HttpRequest.newBuilder()
            .uri(URI.create(host + "/commits/"+ track_id))
            .build();
        HttpResponse<String> commitsRes = webClient.send(deleteCommits, HttpResponse.BodyHandlers.ofString());
        List<Commit> expCommits = new ArrayList<>();
        List<Commit> resCommits = objectMapper.readValue(commitsRes.body().toString(), new TypeReference<List<Commit>>(){});
        Assertions.assertEquals(expCommits, resCommits, "Expected="+expCommits + ", Result="+resCommits);

        HttpRequest deleteMaterial = HttpRequest.newBuilder()
            .uri(URI.create(host + "/materials"))
            .build();
        HttpResponse<String> materialsRes = webClient.send(deleteMaterial, HttpResponse.BodyHandlers.ofString());
        List<Material> resMaterials = objectMapper.readValue(materialsRes.body().toString(), new TypeReference<List<Material>>(){});       
        Assertions.assertFalse(resMaterials.contains(material));

        // Integer material_id = resTrack.getMaterial_from().getMaterial_id();
        // HttpRequest deleteMaterial = HttpRequest.newBuilder()
        //     .uri(URI.create(host + "/materials/"+ material_id))
        //     .build();
        // HttpResponse<String> materialsRes = webClient.send(deleteMaterial, HttpResponse.BodyHandlers.ofString());
        // String resMaterials = materialsRes.body();
        // String expMaterials = "";
        // Assertions.assertEquals(expMaterials, resMaterials, "Expected="+expMaterials + ", Result="+resMaterials);
    }
    @Test
    public void removeTrackWithNonexistedId() throws IOException, InterruptedException {
        Integer track_id = 500;
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(host + "/tracks/"+ track_id))
            .DELETE()
            .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(400, status, "Expected status code 400, actual code is " + status);
        String expected = "";
        String result = response.body();
        Assertions.assertEquals(expected, result, "Expected="+expected + ", Result="+result);
    }
    
}

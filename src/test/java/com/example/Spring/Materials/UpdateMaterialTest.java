package com.example.Spring.Materials;

import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.async.CallableProcessingInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.Spring.StudyTrackSystemApplication;
import com.example.Spring.entity.Material;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

// @RunWit()
// @ExtendWith(SpringExtension.class)
// @WebAppConfiguration
// @ContextHierarchy(@ContextConfiguration(classes = UpdateMaterialTest.WebConfig.class))
// @ContextConfiguration(classes = UpdateMaterialTest.WebConfig.class)
// @DisabledInAot
public class UpdateMaterialTest {
    // @Autowired
    // private WebApplicationContext wac;
    // @Autowired
    // private CallableProcessingInterceptor callableProcessingInterceptor;
    // @Autowired
    private MockMvc mockMvc;
    private WebTestClient testClient;

    ApplicationContext app;
    HttpClient webClient;
    ObjectMapper objectMapper;
    String host = "http://localhost:8080";

    // @EnableWebMvc
    // @Configuration
    // @ComponentScan
    // static class WebConfig implements WebMvcConfigurer {
    //     @Bean
    //     public StudyTrackSystemApplication studyTrackSystemApplication(){
    //         return new StudyTrackSystemApplication();
    //     }
    //     @Bean
    //     public CallableProcessingInterceptor callableProcessingInterceptor(){
    //         return mock();
    //     }
    // }
    @BeforeEach
    public void setup() throws InterruptedException{
    // public void setup(){
        // this.testClient = MockMvcWebTestClient.bindToApplicationContext(this.wac).build();
        // this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        // DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        // this.mockMvc = builder.build();

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
    public void patchMaterialSuccessful() throws Exception {
        String json = "{\"material_id\":30, \"name\":\"git\", \"description\":\"version control\", \"note\":\"learned basic\"}";
        Material expected = new Material(30, "git", "version control", "undefined", "learned basic");

        // MvcResult mvcResult = this.mockMvc.perform(get("/materials").accept(MediaType.APPLICATION_JSON))
        //     .andExpect(status().isOk())
        //     .andExpect(request().asyncStarted())
        //     // .andExpect(request().asyncResult(expected))
        //     .andReturn();

        // MockHttpServletRequestBuilder builder = 
        //     MockMvcRequestBuilders.patch("/materials")
        //         .contentType(MediaType.APPLICATION_JSON_VALUE)
        //         .accept(MediaType.APPLICATION_JSON)
        //         .characterEncoding("UTF-8")
        //         .content(json);
        // this.mockMvc.perform(builder)                
        //     .andExpect(MockMvcResultMatchers.status().isOk())
        //     .andExpect(MockMvcResultMatchers.content().string(expected.toString()))
        //     .andDo(MockMvcResultHandlers.print());

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(host + "/materials"))
            .method("PATCH", HttpRequest.BodyPublishers.ofString(json))
            .header("Content-Type", "application/json")
            .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(200, status, "Expected status code 200, actual code is " + status);
        Material result = objectMapper.readValue(response.body().toString(), new TypeReference<Material>(){});
        Assertions.assertEquals(expected, result, "Expected="+expected + ", Result="+result);
    }   
    @Test
    public void patchMaterialWithInvalidId() throws IOException, InterruptedException {
        String json = "{\"material_id\":300, \"name\":\"git\", \"description\":\"version control\", \"note\":\"learned basic\"}";
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(host + "/materials"))
            .method("PATCH", HttpRequest.BodyPublishers.ofString(json))
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
    public void patchMaterialWithInvalidName() throws IOException, InterruptedException {
        String json = "{\"material_id\":30, \"name\":\"gitt\", \"description\":\"version control\", \"note\":\"learned basic\"}";
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(host + "/materials"))
            .method("PATCH", HttpRequest.BodyPublishers.ofString(json))
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

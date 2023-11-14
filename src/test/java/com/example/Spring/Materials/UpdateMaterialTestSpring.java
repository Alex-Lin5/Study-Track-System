package com.example.Spring.Materials;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.Spring.entity.Material;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = UpdateMaterialTestSpring.WebConfig.class)
public class UpdateMaterialTestSpring {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    // private WebTestClient testClient;
    private String host = "http://localhost:8080";

    @EnableWebMvc
    @Configuration
    static class WebConfig implements WebMvcConfigurer {    
        
    }
    @BeforeEach
    public void setup(){
        // this.testClient = MockMvcWebTestClient.bindToApplicationContext(this.wac).build();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
    @AfterEach
    public void teardown(){

    }
    // @Test
    // public void patchMaterialSuccessful() throws Exception{
    //     String json = "{\"material_id\":30, \"name\":\"git\", \"description\":\"version control\", \"note\":\"learned basic\"}";
    //     Material expected = new Material(30, "git", "version control", "undefined", "learned basic");
    //     MockHttpServletRequestBuilder builder = 
    //         MockMvcRequestBuilders.patch(host + "/materials")
    //             .contentType(MediaType.APPLICATION_JSON_VALUE)
    //             .accept(MediaType.APPLICATION_JSON)
    //             .characterEncoding("UTF-8")
    //             .content(json);
    //     this.mockMvc.perform(builder)
    //         .andExpect(MockMvcResultMatchers.status().isOk())
    //         .andExpect(MockMvcResultMatchers.content().string(expected.toString()))
    //         .andDo(MockMvcResultHandlers.print());
    // }
}

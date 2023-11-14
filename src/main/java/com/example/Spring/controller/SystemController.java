package com.example.Spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Spring.controller.components.CommitController;
import com.example.Spring.controller.components.MaterialController;
import com.example.Spring.controller.components.TrackController;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class SystemController {
    CommitController commitController;
    MaterialController materialController;
    TrackController trackController;
    
    @Autowired
    public SystemController(CommitController cc, MaterialController mc, TrackController tc){
        log.info("System controller initialized.");
        this.commitController = cc;
        this.materialController = mc;
        this.trackController = tc;
    }
    @GetMapping(value = "/")
    public ResponseEntity<String> getHome(){
        log.info("Handling home page get request.");
        return ResponseEntity.status(HttpStatus.OK).body("Home");
    }
}

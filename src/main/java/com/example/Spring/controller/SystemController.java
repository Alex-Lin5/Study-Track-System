package com.example.Spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Spring.entity.Commit;
import com.example.Spring.entity.Material;
import com.example.Spring.entity.Track;
import com.example.Spring.service.CommitService;
import com.example.Spring.service.MaterialService;
import com.example.Spring.service.TrackService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class SystemController {
    CommitService commitService;
    MaterialService materialService;
    TrackService trackService;
    
    @Autowired
    public SystemController(CommitService cs, MaterialService ms, TrackService ts){
        log.info("Controller initialized.");
        this.commitService = cs;
        this.materialService = ms;
        this.trackService = ts;
    }
    @GetMapping(value = "/")
    public ResponseEntity<String> getHome(){
        log.info("Handling home page get request.");
        return ResponseEntity.status(HttpStatus.OK).body("Home");
    }
    @GetMapping(value = "/commits")
    public ResponseEntity<List<Commit>> getAllCommits(){
        List<Commit> commits = commitService.getAllCommits();
        log.info("Handling get all commits request." + commits);
        return ResponseEntity.status(HttpStatus.OK).body(commits);
    }
    @GetMapping(value = "/materials")
    public ResponseEntity<List<Material>> getAllmaterials(){
        List<Material> materials = materialService.getAllMaterials();
        log.info("Handling get all materials request." + materials);
        return ResponseEntity.status(HttpStatus.OK).body(materials);
    }
    @GetMapping(value = "/tracks")
    public ResponseEntity<List<Track>> getAlltracks(){
        List<Track> tracks = trackService.getAllTracks();
        log.info("Handling get all tracks request." + tracks);
        return ResponseEntity.status(HttpStatus.OK).body(tracks);
    }

}

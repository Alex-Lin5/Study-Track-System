package com.example.Spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Spring.entity.Commit;
import com.example.Spring.service.CommitService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class SystemController {
    CommitService commitService;
    
    @Autowired
    public SystemController(CommitService cs){
        log.info("Controller initialized.");
        this.commitService = cs;
    }

    @GetMapping(value = "/commits")
    public ResponseEntity<List<Commit>> getAllCommits(){
        List<Commit> commits = commitService.getAllCommits();
        log.info("Handling get all commits request." + commits);
        return ResponseEntity.status(HttpStatus.OK).body(commits);
    }
}

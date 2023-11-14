package com.example.Spring.controller.components;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Spring.entity.Commit;
import com.example.Spring.service.CommitService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(value = "/commits")
@Log4j2
public class CommitController {
    CommitService commitService;
    @Autowired
    public CommitController(CommitService cs){
        this.commitService = cs;
    }
    @GetMapping(value = "")
    public ResponseEntity<List<Commit>> getAllCommits(){
        List<Commit> commits = commitService.getAllCommits();
        log.info("Handling get all commits request." + commits);
        return ResponseEntity.status(HttpStatus.OK).body(commits);
    }
    @GetMapping(value = "{track_id}")
    public ResponseEntity<List<Commit>> getCommitsInTrack(@PathVariable Integer track_id){
        List<Commit> commits = commitService.getCommitsInTrack(track_id);
        log.info("Handling get commits in track request." + commits + ", track_id=" + track_id);
        return ResponseEntity.status(HttpStatus.OK).body(commits);
    }
    // unlink with track then delete single commit
    @DeleteMapping(value = "{commit_id}")
    public ResponseEntity<Commit> deleteCommitById(@PathVariable Integer commit_id){
        log.info("Handling delete commit request. ID = " + commit_id);
        // if(!commit_id.getClass().equals(Integer.class)){
        //     log.info("Input provided is not an integer.");
        //     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        // }
        Commit commit = commitService.deleteCommitById((Integer) commit_id);
        if(commit == null){
            log.error("Commit is not existed in database, ID = "+ commit_id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        log.info("Delete commit in success, ID = "+ commit_id);
        return ResponseEntity.status(HttpStatus.OK).body(commit);
    }
    @PostMapping(value = "")
    public ResponseEntity<Commit> postCommit(@RequestBody Commit c){
        log.info("Handling post commit request. " + c);
        if(c == null){
            log.error("Post commit with empty input.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Commit commit = commitService.postCommit(c);
        if(commit == null){
            log.error("Post commit with unknown error, "+ c);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        if(c.getFrom() == null){
            log.info("Post commit with empty track from input.");
            return ResponseEntity.status(HttpStatus.OK).body(commit);
        }
        else if(commit.getFrom() == null){
            log.error("Post commit with unknown track from, "+ c.getFrom().getTrack_id());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        log.info("Post commit with valid track from input.");
        return ResponseEntity.status(HttpStatus.OK).body(commit);
    }

}

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

import com.example.Spring.entity.Track;
import com.example.Spring.service.TrackService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(value = "/tracks")
@Log4j2
public class TrackController {
    TrackService trackService;
    @Autowired
    public TrackController(TrackService ts){
        this.trackService = ts;
    }
    @GetMapping(value = "")
    public ResponseEntity<List<Track>> getAlltracks(){
        List<Track> tracks = trackService.getAllTracks();
        log.info("Handling get all tracks request." + tracks);
        return ResponseEntity.status(HttpStatus.OK).body(tracks);
    }
    // unlink with commits and material, then delete single track, commits in this track and binding material
    @DeleteMapping(value = "{track_id}")
    public ResponseEntity<Track> deleteTrackById(@PathVariable Integer track_id){
        log.info("Handling delete track request. ID = " + track_id);
        Track track = trackService.deleteTrackById((Integer) track_id);
        if(track == null){
            log.error("Track is not existed in database, ID = "+ track_id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        log.info("Delete track in success, ID = "+ track_id);
        return ResponseEntity.status(HttpStatus.OK).body(track);
    }
    @PostMapping(value = "")
    public ResponseEntity<Track> postTrack(@RequestBody Track t){
        if(t == null){
            log.error("Post track with empty input.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        if(t.getMaterial_from() == null){
            log.error("Post track with empty material from input.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        log.info("Handling post track request. " + t);
        Track track = trackService.postTrack(t);
        if(track == null){
            log.error("Post track with unknown error, "+ t);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        else if(track.getMaterial_from() == null){
            log.error("Post track with non-existed material from, "+ t.getMaterial_from().getName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        else if(!track.getMaterial_from().getMaterial_id().equals(t.getMaterial_from().getMaterial_id())){
            log.error("Post track duplicate with existed material from, "+ t.getMaterial_from().getName()+ ", track id is "+ track.getTrack_id());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        log.info("Post track with valid input.");
        return ResponseEntity.status(HttpStatus.OK).body(track);
    }
}

package com.example.Spring.controller;

import java.util.List;

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
    @GetMapping(value = "/commits/{track_id}")
    public ResponseEntity<List<Commit>> getCommitsInTrack(@PathVariable Integer track_id){
        List<Commit> commits = commitService.getCommitsInTrack(track_id);
        log.info("Handling get commits in track request." + commits + ", track_id=" + track_id);
        return ResponseEntity.status(HttpStatus.OK).body(commits);
    }
    // unlink with track then delete single commit
    @DeleteMapping(value = "commits/{commit_id}")
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
    @PostMapping(value = "/commits")
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

    @GetMapping(value = "/materials")
    public ResponseEntity<List<Material>> getAllmaterials(){
        List<Material> materials = materialService.getAllMaterials();
        log.info("Handling get all materials request." + materials);
        return ResponseEntity.status(HttpStatus.OK).body(materials);
    }
    // @GetMapping(value = "/material/{material_id}")
    // public ResponseEntity<Material> getMaterialById(@PathVariable Integer material_id){
    //     Material material = materialService.findById(material_id);
    // }
    // unlink with track then delete single material
    @DeleteMapping(value = "/materials/{material_id}")
    public ResponseEntity<Material> deleteMaterialById(@PathVariable Integer material_id){
        log.info("Handling delete material request. ID = " + material_id);
        Material material = materialService.deleteMaterialById((Integer) material_id);
        if(material == null){
            log.error("Material is not existed in database, ID = "+ material_id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        log.info("Delete material in success, ID = "+ material_id);
        return ResponseEntity.status(HttpStatus.OK).body(material);
    }
    @PatchMapping(value = "/materials")
    public ResponseEntity<Material> patchMaterial(@RequestBody Material m){
        log.info("Handling patch material request. " + m);
        if(m == null){
            log.error("Patch material with empty input.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Material material = materialService.patchMaterial(m);
        if(material == null){
            log.error("Patch material with unknown error, "+ m);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        if(material.getName() == null){
            log.error("Patch material with unmatched name attribute, "+ m.getName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(material);
    }
    @PutMapping(value = "/materials")
    public ResponseEntity<Material> putMaterial(@RequestBody Material m){
        log.info("Handling put material request. " + m);
        if(m == null){
            log.error("Put material with empty input.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Material material = materialService.putMaterial(m);
        if(material == null){
            log.error("Put material with unknown error, "+ m);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        if(material.getName() == null){
            log.error("Put material with conflict name attribute in database, "+ m.getName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(material);
    }
    @PostMapping(value = "/materials")
    public ResponseEntity<Material> postMaterial(@RequestBody Material m){
        log.info("Handling post material request. " + m);
        if(m == null){
            log.error("Post material with empty input.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Material material = materialService.postMaterial(m);
        if(material == null){
            log.error("Post material with unknown error, "+ m);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        // if(material.getMaterial_id() == null){
        //     log.error("Post material with duplicate id attribute, "+ m.getMaterial_id());
        //     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        // }
        if(material.getName() == null){
            log.error("Post material with duplicate name attribute, "+ m.getName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(material);
    }

    @GetMapping(value = "/tracks")
    public ResponseEntity<List<Track>> getAlltracks(){
        List<Track> tracks = trackService.getAllTracks();
        log.info("Handling get all tracks request." + tracks);
        return ResponseEntity.status(HttpStatus.OK).body(tracks);
    }
    // unlink with commits and material, then delete single track, commits in this track and binding material
    @DeleteMapping(value = "tracks/{track_id}")
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
    @PostMapping(value = "/tracks")
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

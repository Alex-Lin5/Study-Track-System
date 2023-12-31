package com.example.Spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Spring.entity.Track;
import com.example.Spring.entity.Commit;
import com.example.Spring.entity.Material;
import com.example.Spring.repository.CommitRepository;
import com.example.Spring.repository.MaterialRepository;
import com.example.Spring.repository.TrackRepository;

@Service
public class TrackService {
    @Autowired
    CommitRepository commitRepository;
    @Autowired
    MaterialRepository materialRepository;
    TrackRepository trackRepository;
    @Autowired
    public TrackService(TrackRepository tr){
        this.trackRepository = tr;
    }
    public List<Track> getAllTracks(){
        List<Track> tracks = trackRepository.findAll();
        return tracks;
    }
    public Track postTrack(Track t){
        if(t == null) return null;
        if(t.getMaterial_from() == null) return null;
        Track track = new Track(t);
        Optional<Material> existedMaterial = materialRepository.findById(t.getMaterial_from().getMaterial_id());
        Optional<Track> existedTrack = trackRepository.findByMaterial(t.getMaterial_from().getMaterial_id());
        if(existedMaterial.isPresent()){
            track.setMaterial_from(existedMaterial.get());
        }
        if(existedMaterial.isEmpty()){
            track.setMaterial_from(null); 
            return track;
        }
        if(existedTrack.isPresent()){
            track.setMaterial_from(new Material(t.getMaterial_from().getMaterial_id()+1));
            return track;
        }
        return trackRepository.save(track);
    }
    public Track deleteTrackById(Integer track_id){
        if(track_id == null) return null;
        Optional<Track> existedTrack = trackRepository.findById(track_id);
        if(existedTrack.isPresent()){
            Track track = existedTrack.get();
            List<Commit> commits = commitRepository.findAllInTrack(track_id);
            for(Commit c : commits){
                c.setFrom(null);
                commitRepository.save(c);
                commitRepository.delete(c);
            }
            // Material material = materialRepository.findById(track.getMaterial_from().getMaterial_id()).get();
            // materialRepository.save(material);
            Integer material_id = track.getMaterial_from().getMaterial_id();
            track.setMaterial_from(null);
            trackRepository.save(track);
            materialRepository.deleteById(material_id);
            trackRepository.deleteById(track_id);
            track.setMaterial_from(new Material(material_id));
            return track;
        }
        return null;
    }
}

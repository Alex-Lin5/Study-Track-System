package com.example.Spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Spring.entity.Commit;
import com.example.Spring.entity.Track;
import com.example.Spring.repository.CommitRepository;
import com.example.Spring.repository.TrackRepository;

@Service
public class CommitService {
    CommitRepository commitRepository;
    @Autowired
    TrackRepository trackRepository;
    @Autowired
    public CommitService(CommitRepository commitRepository){
        this.commitRepository = commitRepository;
    }
    public List<Commit> getAllCommits(){
        List<Commit> commits = commitRepository.findAll();
        return commits;
    }
    public List<Commit> getCommitsInTrack(Integer track_id){
        List<Commit> commits = commitRepository.findAllInTrack(track_id);
        return commits;
    }
    public Commit postCommit(Commit c){
        if(c.getFrom() != null){
            Optional<Track> trackFrom = trackRepository.findById(c.getFrom().getTrack_id());
            Commit commit = new Commit(c);
            if(trackFrom.isEmpty()){
                commit.setFrom(null);
            }
            if(trackFrom.isPresent()){
                commit.setFrom(trackFrom.get());
            }
            return commitRepository.save(commit);
        }
        return commitRepository.save(c);
    }
    public Commit deleteCommitById(Integer id){
        if(id == null) return null;
        Optional<Commit> existedCommit = commitRepository.findById(id);
        if(existedCommit.isPresent()){
            Commit commit = existedCommit.get();
            commit.setFrom(null);
            commitRepository.save(commit);
            commitRepository.deleteById(id);
            return existedCommit.get();
        }
        return null;
    }
}

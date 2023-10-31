package com.example.Spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Spring.entity.Track;
import com.example.Spring.repository.TrackRepository;

@Service
public class TrackService {
    TrackRepository trackRepository;
    @Autowired
    public TrackService(TrackRepository tr){
        this.trackRepository = tr;
    }
    public List<Track> getAllTracks(){
        List<Track> tracks = trackRepository.findAll();
        return tracks;
    }
}

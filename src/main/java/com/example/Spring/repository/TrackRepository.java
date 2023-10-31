package com.example.Spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Spring.entity.Track;

public interface TrackRepository extends JpaRepository<Track, Integer>{
    
}

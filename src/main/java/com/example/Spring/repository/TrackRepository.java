package com.example.Spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Spring.entity.Track;

@Repository
public interface TrackRepository extends JpaRepository<Track, Integer>{
    @Query(value = "SELECT * FROM Track WHERE material_from = :mid", nativeQuery = true)
    Optional<Track> findByMaterial(@Param("mid") Integer material_id);
}

package com.example.Spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Spring.entity.Commit;

@Repository
public interface CommitRepository extends JpaRepository<Commit, Integer>{
    // @Query("From Commit WHERE date = :datevar")
    // Optional<Commit> findByDate(@Param("datevar") Long date);
    @Query(value = "SELECT * FROM Commit WHERE track_from = :track_id", nativeQuery = true)
    List<Commit> findAllInTrack(@Param("track_id") Integer id);
}

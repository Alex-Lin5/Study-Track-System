package com.example.Spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Spring.entity.Material;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer>{
    @Query("FROM Material WHERE name = :namevar")
    Optional<Material> findByName(@Param("namevar") String name);
    
}

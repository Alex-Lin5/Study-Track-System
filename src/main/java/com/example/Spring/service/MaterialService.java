package com.example.Spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Spring.entity.Material;
import com.example.Spring.entity.Track;
import com.example.Spring.repository.MaterialRepository;
import com.example.Spring.repository.TrackRepository;

// import lombok.extern.log4j.Log4j2;

@Service
// @Log4j2
public class MaterialService {
    @Autowired
    TrackRepository trackRepository;
    MaterialRepository materialRepository;
    @Autowired
    public MaterialService(MaterialRepository mr){
        this.materialRepository = mr;
    }
    public List<Material> getAllMaterials(){
        List<Material> materials = materialRepository.findAll();
        return materials;
    }
    public Material postMaterial(Material m){
        Optional<Material> DuplicateName = materialRepository.findByName(m.getName());
        // Optional<Material> DuplicateId = materialRepository.findById(m.getMaterial_material_id());
        // if(DuplicateId.isPresent()){
        //     Material material = new Material(m);
        //     return material;
        // }
        if(DuplicateName.isPresent()){
            Material material = new Material(m);
            material.setName(null);
            return material;
        }
        return materialRepository.save(m);
    }
    public Material deleteMaterialById(Integer material_id){
        if(material_id == null) return null;
        Optional<Material> existedMaterial = materialRepository.findById(material_id);
        if(existedMaterial.isPresent()){
            Track track = trackRepository.findByMaterial(material_id).get();
            track.setMaterial_from(null);
            Track returnedTrack = trackRepository.save(track);
            // log.info("returned track from material deletion, "+ returnedTrack);
            materialRepository.deleteById(material_id);
            return existedMaterial.get();
        }
        return null;
    }
    public Material patchMaterial(Material m){
        Optional<Material> optionalMaterial = materialRepository.findById(m.getMaterial_id());
        if(optionalMaterial.isEmpty()) return null;
        Material material = new Material(m);
        if(!optionalMaterial.get().getName().equals(m.getName())){
            material.setName(null);
            return material;
        } 
        return materialRepository.save(material);
    }
    public Material putMaterial(Material m){
        if(m == null) return null;
        Optional<Material> optionalMaterial = materialRepository.findById(m.getMaterial_id());
        Optional<Material> nameMaterial = materialRepository.findByName(m.getName());
        if(optionalMaterial.isEmpty()) return null;
        Material material = new Material(m);
        if(nameMaterial.isPresent()){
            material.setName(null);
            return material;
        } 
        return materialRepository.save(material);
    }
}

package com.example.Spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Spring.entity.Material;
import com.example.Spring.repository.MaterialRepository;

@Service
public class MaterialService {
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
        Optional<Material> DuplicateId = materialRepository.findById(m.getMaterial_id());
        if(DuplicateId.isPresent()){
            Material material = new Material(m, false);
            return material;
        }
        if(DuplicateName.isPresent()){
            Material material = new Material(m, true);
            material.setName(null);
            return material;
        }
        return materialRepository.save(m);
    }
}

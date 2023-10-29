package com.example.Spring.service;

import java.util.List;

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
}

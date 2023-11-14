package com.example.Spring.controller.components;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Spring.entity.Material;
import com.example.Spring.service.MaterialService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(value = "/materials")
@Log4j2
public class MaterialController {
    MaterialService materialService;
    @Autowired
    public MaterialController(MaterialService ms){
        this.materialService = ms;
    }
    @GetMapping(value = "")
    public ResponseEntity<List<Material>> getAllmaterials(){
        List<Material> materials = materialService.getAllMaterials();
        log.info("Handling get all materials request." + materials);
        return ResponseEntity.status(HttpStatus.OK).body(materials);
    }
    // @GetMapping(value = "/material/{material_id}")
    // public ResponseEntity<Material> getMaterialById(@PathVariable Integer material_id){
    //     Material material = materialService.findById(material_id);
    // }
    // unlink with track then delete single material
    @DeleteMapping(value = "{material_id}")
    public ResponseEntity<Material> deleteMaterialById(@PathVariable Integer material_id){
        log.info("Handling delete material request. ID = " + material_id);
        Material material = materialService.deleteMaterialById((Integer) material_id);
        if(material == null){
            log.error("Material is not existed in database, ID = "+ material_id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        log.info("Delete material in success, ID = "+ material_id);
        return ResponseEntity.status(HttpStatus.OK).body(material);
    }
    @PatchMapping(value = "")
    public ResponseEntity<Material> patchMaterial(@RequestBody Material m){
        log.info("Handling patch material request. " + m);
        if(m == null){
            log.error("Patch material with empty input.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Material material = materialService.patchMaterial(m);
        if(material == null){
            log.error("Patch material with unknown error, "+ m);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        if(material.getName() == null){
            log.error("Patch material with unmatched name attribute, "+ m.getName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(material);
    }
    @PutMapping(value = "")
    public ResponseEntity<Material> putMaterial(@RequestBody Material m){
        log.info("Handling put material request. " + m);
        if(m == null){
            log.error("Put material with empty input.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Material material = materialService.putMaterial(m);
        if(material == null){
            log.error("Put material with unknown error, "+ m);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        if(material.getName() == null){
            log.error("Put material with conflict name attribute in database, "+ m.getName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(material);
    }
    @PostMapping(value = "")
    public ResponseEntity<Material> postMaterial(@RequestBody Material m){
        log.info("Handling post material request. " + m);
        if(m == null){
            log.error("Post material with empty input.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Material material = materialService.postMaterial(m);
        if(material == null){
            log.error("Post material with unknown error, "+ m);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        // if(material.getMaterial_id() == null){
        //     log.error("Post material with duplicate id attribute, "+ m.getMaterial_id());
        //     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        // }
        if(material.getName() == null){
            log.error("Post material with duplicate name attribute, "+ m.getName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(material);
    }

}

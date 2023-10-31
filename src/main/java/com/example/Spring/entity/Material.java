package com.example.Spring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Data
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private Integer material_id;
    private String name;
    private String description;
    @Column(columnDefinition = "varchar(255) default 'undefined'")
    private String url;
    @Column(columnDefinition = "varchar(255) default 'unknown'")
    private String note;
    // @OneToOne(mappedBy = "material")
    // private Track track;

    public Material(){}
    public Material(Integer id, String name, String des, String url, String note){
        this.material_id = id;
        this.name = name;
        this.description = des;
        this.url = url;
        this.note = note;
    }
    public Material(String name, String des, String url, String note){
        this.name = name;
        this.description = des;
        this.url = url;
        this.note = note;
    }
    public Material(Integer id, String name, String des){
        this.material_id = id;
        this.name = name;
        this.description = des;
    }


    @Override
    public int hashCode(){
        return this.material_id;
    }
    @Override
    public boolean equals(Object obj){
        if(obj == null) return false;
        if(this == obj) return true;
        if(getClass() != obj.getClass()) return false;
        Material other = (Material) obj;
        if(this.material_id == null){
            if(other.material_id != null) return false;            
        } else if(!this.material_id.equals(other.material_id)){
            return false;
        }
        if(this.name == null){
            if(other.name != null) return false;            
        } else if(!this.name.equals(other.name)){
            return false;
        }
        if(this.description == null){
            if(other.description != null) return false;            
        } else if(!this.description.equals(other.description)){
            return false;
        }
        if(this.url == null){
            if(other.url != null) return false;            
        } else if(!this.url.equals(other.url)){
            return false;
        }
        if(this.note == null){
            if(other.note != null) return false;            
        } else if(!this.note.equals(other.note)){
            return false;
        }
        return true;
    }
    
    @Override
    public String toString(){
        return String.format(
            "Material {material_id=%d, name=%s, url=%s, description=%s}\n", 
            material_id, name, url, description);
    }

}

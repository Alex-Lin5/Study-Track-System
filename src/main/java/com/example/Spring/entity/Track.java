package com.example.Spring.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Data
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer track_id;
    // @OneToMany(cascade = CascadeType.ALL)
    // @JoinColumn(name = "commit_id")
    // private List<Commit> commits;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "material_from")
    private Material material_from;
    @Column(columnDefinition = "integer default 0")
    private Integer progress;

    public Track(){}
    public Track(Integer id, Material material, Integer progress){
        this.track_id = id;
        this.material_from = material;
        this.progress = progress;
    }

    @Override
    public int hashCode(){
        return this.track_id;
    }
    @Override
    public boolean equals(Object obj){
        if(obj == null) return false;
        if(this == obj) return true;
        if(getClass() != obj.getClass()) return false;
        Track other = (Track) obj;
        if(this.track_id == null){
            if(other.track_id != null) return false;            
        } else if(!this.track_id.equals(other.track_id)){
            return false;
        }
        if(this.progress == null){
            if(other.progress != null) return false;            
        } else if(!this.progress.equals(other.progress)){
            return false;
        }
        if(this.material_from == null){
            if(other.material_from != null) return false;            
        } else if(!this.material_from.getMaterial_id().equals(other.material_from.getMaterial_id())){
            return false;
        }
        return true;
    }

    @Override
    public String toString(){
        return String.format(
            "Track {track_id=%d, material=%s, progress=%d}\n", 
            track_id, material_from.getName(), progress);
    }

}

package com.example.Spring.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
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
    @OneToMany(cascade = CascadeType.ALL)
    // @JoinColumn(name = "commit_id")
    // @Column(name = "commits", columnDefinition = "integer")
    private List<Commit> commits;
    @OneToOne(cascade = CascadeType.ALL)
    // @PrimaryKeyJoinColumn
    @JoinColumn(name = "material", referencedColumnName = "material_id")
    private Material material;
    @Column(columnDefinition = "integer default 0")
    private Integer progress;

    public Track(){}
    public Track(Integer id, List<Commit> commits, Material material){
        this.track_id = id;
        this.commits = commits;
        this.material = material;
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
        return true;
    }

    @Override
    public String toString(){
        return String.format(
            "Track {track_id=%d, material=%s, progress=%d}\n", 
            track_id, material.getName(), progress);
    }

}

package com.example.Spring.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Data
public class Commit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private Integer commit_id;
    private Long date_posted;
    private Long start_hour;
    private Long end_hour;
    
    public Commit(){}
    public Commit(Integer id, Long dp, Long sh, Long eh){
        this.commit_id = id;
        this.date_posted = dp;
        this.start_hour = sh;
        this.end_hour = eh;
    }

    @Override
    public int hashCode(){
        return this.commit_id;
    }
    @Override
    public boolean equals(Object obj){
        if(obj == null) return false;
        if(this == obj) return true;
        if(getClass() != obj.getClass()) return false;
        Commit other = (Commit) obj;
        if(this.commit_id == null){
            if(other.commit_id != null) return false;            
        } else if(!this.commit_id.equals(other.commit_id)){
            return false;
        }
        if(this.date_posted == null){
            if(other.date_posted != null) return false;            
        } else if(!this.date_posted.equals(other.date_posted)){
            return false;
        }
        if(this.start_hour == null){
            if(other.start_hour != null) return false;            
        } else if(!this.start_hour.equals(other.start_hour)){
            return false;
        }
        if(this.end_hour == null){
            if(other.end_hour != null) return false;            
        } else if(!this.end_hour.equals(other.end_hour)){
            return false;
        }
        return true;
    }
    
    @Override
    public String toString(){
        return String.format(
            "Commit {commit_id=%d, date_posted=%d, start_hour=%d, end_hour=%d}", 
            commit_id, date_posted, start_hour, end_hour);
    }
}

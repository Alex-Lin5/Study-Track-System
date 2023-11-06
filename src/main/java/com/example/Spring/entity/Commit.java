package com.example.Spring.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @Column(columnDefinition = "varchar(255) default 'None'")
    private String message = "None";
    private Long date_posted;
    private Long start_hour;
    private Long end_hour;
    @Column(columnDefinition = "integer default 10")
    private Integer point;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "track_from")
    // @JoinColumn(name = "from", referencedColumnName = "track_id")
    private Track from;

    public Commit(){}
    public Commit(Commit c){
        this.commit_id = c.getCommit_id();
        this.date_posted = c.getDate_posted();
        this.start_hour = c.getStart_hour();
        this.end_hour = c.getEnd_hour();
    }
    public Commit(Integer id, Long dp, Long sh, Long eh){
        this.commit_id = id;
        this.date_posted = dp;
        this.start_hour = sh;
        this.end_hour = eh;
    }
    public Commit(Integer id, Long dp, Long sh, Long eh, Track from){
        this.commit_id = id;
        this.date_posted = dp;
        this.start_hour = sh;
        this.end_hour = eh;
        this.from = from;
    }
    // public Long getHours(){
    //     return end_hour-start_hour;
    // }

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
        if(from != null)
            return String.format(
            "Commit {commit_id=%d, message=%s date_posted=%d, start_hour=%d, end_hour=%d, from=%d}\n", 
            commit_id, message, date_posted, start_hour, end_hour, from.getTrack_id());
        return String.format(
            "Commit {commit_id=%d, message=%s date_posted=%d, start_hour=%d, end_hour=%d}\n", 
            commit_id, message, date_posted, start_hour, end_hour);
    }
}

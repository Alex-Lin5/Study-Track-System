package com.example.Spring.entity;

import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

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
    public boolean equals(@Nullable Object obj){
        if(obj == null) return false;
        if(this == obj) return true;
        if(getClass() != obj.getClass()) return false;
        Commit that = (Commit) obj;
        return ObjectUtils.nullSafeEquals(this.commit_id, that.commit_id) &&
            ObjectUtils.nullSafeEquals(this.date_posted, that.date_posted) &&
            ObjectUtils.nullSafeEquals(this.start_hour, that.start_hour) &&
            ObjectUtils.nullSafeEquals(this.end_hour, that.end_hour);
    }
    
    @Override
    public String toString(){
        if(from != null)
            return String.format(
            "Commit {commit_id=%d, message=%s date_posted=%d, start_hour=%d, end_hour=%d, from=%d}\n", 
            commit_id, message, date_posted, start_hour, end_hour, from.getTrack_id());
        return String.format(
            "Commit {commit_id=%d, message=%s date_posted=%d, start_hour=%d, end_hour=%d, from=null}\n", 
            commit_id, message, date_posted, start_hour, end_hour);
    }
}

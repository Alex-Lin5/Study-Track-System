package com.example.Spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Spring.entity.Commit;
import com.example.Spring.repository.CommitRepository;

@Service
public class CommitService {
    CommitRepository commitRepository;
    @Autowired
    public CommitService(CommitRepository commitRepository){
        this.commitRepository = commitRepository;
    }
    public List<Commit> getAllCommits(){
        List<Commit> commits = commitRepository.findAll();
        return commits;
    }
}

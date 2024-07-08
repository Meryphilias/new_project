package com.ppt.autoppt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ppt.autoppt.Entity.bookEntity;

@Repository
public interface bookrepository extends JpaRepository<bookEntity, Integer>{

    
}
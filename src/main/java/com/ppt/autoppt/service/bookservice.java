package com.ppt.autoppt.service;

import org.springframework.stereotype.Service;

import com.ppt.autoppt.Entity.bookEntity;
import com.ppt.autoppt.repository.bookrepository;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;


@Service
public class bookservice {

    @Autowired
    private bookrepository bookrepository;


    public void savebook(String name){

        bookEntity book = new bookEntity();

        book.bookname = name;

        bookrepository.save(book);
    }

    public List<bookEntity> callbookAll(){

        return bookrepository.findAll();

    }

    public void removebookAll(){
        bookrepository.deleteAll();
    }
    
}

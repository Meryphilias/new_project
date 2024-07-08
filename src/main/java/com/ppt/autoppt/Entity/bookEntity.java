package com.ppt.autoppt.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class bookEntity {

    @Id
    @GeneratedValue
    public Integer id;
    public String bookname;
    
}

package com.ppt.autoppt.Entity;

import org.hibernate.mapping.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class imageurlEntity {

    @Id
    @GeneratedValue
    public Integer id;
    public String image_urls;
    
}
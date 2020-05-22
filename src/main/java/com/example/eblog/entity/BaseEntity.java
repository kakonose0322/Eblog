package com.example.eblog.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author zwp
 * @create 2020-05-21 21:14
 */
@Data
public class BaseEntity {
    private Long id;
    private Date created;
    private Date modified ;
}

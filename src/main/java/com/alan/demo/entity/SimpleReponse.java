package com.alan.demo.entity;

import lombok.Data;

@Data
public class SimpleReponse {

    private boolean succesed;
    private int code;
    private String message;
}

package com.shop.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class SearchParam implements Serializable{

    private String keyword;

    private String catalog3Id;

    private String[] valueId;
}

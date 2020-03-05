package com.shop.bean;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.io.Serializable;

@Setter
@Getter
public class PmsBaseCatalog3 implements Serializable {

    @Id
    private String id;
    private String name;
    private String catalog2Id;

}

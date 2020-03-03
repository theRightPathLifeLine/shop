package com.shop.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @param
 * @return
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PmsProductInfo implements Serializable {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column
    private String productName;

    @Column
    private String description;

    @Column
    private  String catalog3Id;

    @Transient
    private List<PmsProductSaleAttr> pmsProductSaleAttrList;
    @Transient
    private List<PmsProductImage> pmsProductImageList;



}



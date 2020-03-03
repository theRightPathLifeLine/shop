package com.shop.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @param
 * @return
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PmsSkuImage implements Serializable {

    @Id
    @Column
    String id;
    @Column
    String skuId;
    @Column
    String imgName;
    @Column
    String imgUrl;
    @Column
    String productImgId;
    @Column
    String isDefault;

}
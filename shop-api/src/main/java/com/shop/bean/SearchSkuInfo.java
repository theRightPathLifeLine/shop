package com.shop.bean;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @param
 * @return
 */
@Setter
@Getter
@ToString
public class SearchSkuInfo implements Serializable {

    String id;

    String productId;

    BigDecimal price;

    String skuName;

    BigDecimal weight;

    String skuDesc;

    String catalog3Id;

    String skuDefaultImg;

    double hot;

    List<PmsSkuAttrValue> pmsSkuAttrValueList;

}

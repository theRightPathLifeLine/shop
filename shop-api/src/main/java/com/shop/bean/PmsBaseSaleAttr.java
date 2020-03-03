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
public class PmsBaseSaleAttr implements Serializable {

    @Id
    @Column
    String id ;

    @Column
    String name;

}
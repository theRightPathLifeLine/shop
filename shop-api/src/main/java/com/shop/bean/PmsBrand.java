package com.shop.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PmsBrand implements Serializable {

    @Id
    private String id;
    private String name;
    private String firstLetter;
    private int sort;
    private int factoryStatus;
    private int showStatus;
    private int productCount;
    private String productCommentCount;
    private String logo;
    private String bigPic;
    private String brandStory;

}

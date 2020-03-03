package com.shop.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @param
 * @return
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PaymentInfo {

    @Column
    @Id
    private String  id;

    @Column
    private String outTradeNo;

    @Column
    private String orderId;

    @Column
    private String alipayTradeNo;

    @Column
    private BigDecimal totalAmount;

    @Column
    private String Subject;

    @Column
    private String paymentStatus;

    @Column
    private Date createTime;

    @Column
    private Date callbackTime;

    @Column
    private String callbackContent;

}

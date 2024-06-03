package org.dubbo.distribute.design.StrategyDesign.Stragegy;

import java.math.BigDecimal;

/**
 * @description: some desc
 * @author: muqingfeng
 * @date: 2024/5/26 10:13
 */
public class context<T> {

    private final ICouponDiscount<T> couponDiscount;


    public context(ICouponDiscount<T> couponDiscount) {
        this.couponDiscount = couponDiscount;
    }

    public BigDecimal discountAmount(T couponInfo, BigDecimal skuPrice) {
        return couponDiscount.discountAmount(couponInfo, skuPrice);
    }

}

package org.dubbo.distribute.design.StrategyDesign.Stragegy.impl;

import org.dubbo.distribute.design.StrategyDesign.Stragegy.ICouponDiscount;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @description: 折扣计算
 * @author: muqingfeng
 * @date: 2024/5/26 10:03
 */
public class ZKCouponDiscount implements ICouponDiscount<Double> {

    /**
     * 折扣计算
     * 1. 使用商品金额 * 折扣比例， 为最后支付金额
     * 2. 保留两位小数
     * 3. 最低支付1元
     */
    @Override
    public BigDecimal discountAmount(Double couponInfo, BigDecimal skuPrice) {
        BigDecimal price = skuPrice.multiply(new BigDecimal(couponInfo)).setScale(2, RoundingMode.HALF_UP);
        return price.compareTo(BigDecimal.ONE) < 1 ? BigDecimal.ONE : price;
    }
}

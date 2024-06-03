package org.dubbo.distribute.design.StrategyDesign.Stragegy.impl;

import org.dubbo.distribute.design.StrategyDesign.Stragegy.ICouponDiscount;

import java.math.BigDecimal;

/**
 * @description: 直减计算
 * @author: muqingfeng
 * @date: 2024/5/26 09:59
 */
public class ZJCouponDiscount implements ICouponDiscount<Double> {

    /**
     * 直减计算
     * 1.使用商品价格减去优惠价格
     * 2.最低支付1元
     */
    @Override
    public BigDecimal discountAmount(Double couponInfo, BigDecimal skuPrice) {
        BigDecimal subtract = skuPrice.subtract(BigDecimal.valueOf(couponInfo));
        return subtract.compareTo(BigDecimal.ONE) < 0 ? BigDecimal.ONE : subtract;
    }
}

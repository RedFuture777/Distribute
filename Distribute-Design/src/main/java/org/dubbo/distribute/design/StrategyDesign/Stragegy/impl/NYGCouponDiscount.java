package org.dubbo.distribute.design.StrategyDesign.Stragegy.impl;

import org.dubbo.distribute.design.StrategyDesign.Stragegy.ICouponDiscount;

import java.math.BigDecimal;

/**
 * @description: n 元购
 * @author: muqingfeng
 * @date: 2024/5/26 09:56
 */
public class NYGCouponDiscount implements ICouponDiscount<Double> {

    /**
     * 无论原始价格多少钱都固定金额
     * @param couponInfo 固定价格
     * @param skuPrice 单价，原价
     * @return
     */
    @Override
    public BigDecimal discountAmount(Double couponInfo, BigDecimal skuPrice) {
        return new BigDecimal(couponInfo);
    }
}

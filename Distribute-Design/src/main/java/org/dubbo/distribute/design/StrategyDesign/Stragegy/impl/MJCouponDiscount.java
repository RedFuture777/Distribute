package org.dubbo.distribute.design.StrategyDesign.Stragegy.impl;

import org.dubbo.distribute.design.StrategyDesign.Stragegy.ICouponDiscount;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @description: 满减折扣劵计算
 * @author: muqingfeng
 * @date: 2024/5/26 09:47
 */
public class MJCouponDiscount implements ICouponDiscount<Map<String, String>> {

    /**
     * 满减计算
     * 判断满x元后-n元，否则不减
     * 最低支付金额1元
     */
    @Override
    public BigDecimal discountAmount(Map<String, String> couponInfo, BigDecimal skuPrice) {
        String x = couponInfo.get("x");
        String n = couponInfo.get("n");

        //小于商品金额的，直接返回原价
        if(skuPrice.compareTo(new BigDecimal(x)) < 0) return skuPrice;
        //减去优惠金额判断
        BigDecimal discountAmount = skuPrice.subtract(new BigDecimal(n));
        return discountAmount.compareTo(BigDecimal.ONE) > 0 ? discountAmount : BigDecimal.ONE;
    }
}

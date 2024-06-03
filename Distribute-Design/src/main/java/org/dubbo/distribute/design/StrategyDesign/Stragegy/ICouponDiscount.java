package org.dubbo.distribute.design.StrategyDesign.Stragegy;

import java.math.BigDecimal;

/**
 * @description: interface 用于定义标准的, 优惠卷折扣计算接口
 * @author: muqingfeng
 * @date: 2024/5/26 00:13
 */
public interface ICouponDiscount<T> {


    BigDecimal discountAmount(T couponInfo, BigDecimal skuPrice);

}

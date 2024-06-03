package org.dubbo.distribute.design;

import org.dubbo.distribute.design.StrategyDesign.Stragegy.context;
import org.dubbo.distribute.design.StrategyDesign.Stragegy.impl.MJCouponDiscount;
import org.dubbo.distribute.design.StrategyDesign.Stragegy.impl.NYGCouponDiscount;
import org.dubbo.distribute.design.StrategyDesign.Stragegy.impl.ZJCouponDiscount;
import org.dubbo.distribute.design.StrategyDesign.Stragegy.impl.ZKCouponDiscount;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: some desc
 * @author: muqingfeng
 * @date: 2024/5/26 00:01
 */
public class ApiTest {

    private final Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void test_Strategy() {
        System.out.println("test_Strategy");
    }

    @Test
    public void test_mj() {
        Map<String, String> map = new HashMap<>();
        map.put("x", "400");
        map.put("n", "30");
        context<Map<String, String>> mapcontext = new context<>(new MJCouponDiscount());
        BigDecimal discountAmount = mapcontext.discountAmount(map, new BigDecimal(400));
        logger.info("满减后的金额是：{}", discountAmount);
    }

    @Test
    public void test_nyg() {
        context<Double> nygContext = new context<>(new NYGCouponDiscount());
        BigDecimal discountAmount = nygContext.discountAmount(10D, new BigDecimal(100));
        logger.info("满减后的金额是：{}", discountAmount);
    }

    @Test
    public void test_zj() {
        context<Double> doubleContext = new context<>(new ZJCouponDiscount());
        BigDecimal discountAmount = doubleContext.discountAmount(10D, new BigDecimal(100));
        logger.info("满减后的金额是：{}", discountAmount);
    }

    @Test
    public void test_zk() {
        context<Double> zkContext = new context<>(new ZKCouponDiscount());
        BigDecimal discountAmount = zkContext.discountAmount(0.78, new BigDecimal(100));
        logger.info("满减后的金额是：{}", discountAmount);
    }

}

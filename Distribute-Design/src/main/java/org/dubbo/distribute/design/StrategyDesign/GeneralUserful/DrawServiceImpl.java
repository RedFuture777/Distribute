package org.dubbo.distribute.design.StrategyDesign.GeneralUserful;

/**
 * @description: 一般使用， if-else
 * @author: muqingfeng
 * @date: 2024/5/26 00:09
 */
public class DrawServiceImpl {

    public void draw(int type) {
        if (1 == type) {
            System.out.println("执行 1 类型");
        }

        if (2 == type) {
            System.out.println("执行 2 类型");
        }

        if (3 == type) {
            System.out.println("执行 3 类型");
        }

        if (4 == type) {
            System.out.println("执行 4 类型");
        }

    }
}

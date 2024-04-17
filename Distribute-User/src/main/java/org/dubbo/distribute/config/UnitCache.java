package org.dubbo.distribute.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Service;

/**
 * @description: 实际缓存配置类
 * @author: muqingfeng
 * @date: 2024/4/18 00:25
 */

@EqualsAndHashCode
@Service
@Data
public class UnitCache extends BaseCaffeineCacheConfig{

    @Override
    public Object getValue(Object key) {
        return null;
    }
}

package cn.cug.sxy.infrastructure.persistent.dao;

import cn.cug.sxy.infrastructure.persistent.po.SCSkuActivity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @version 1.0
 * @Date 2025/3/30 14:02
 * @Description 渠道商品活动配置 Dao
 * @Author jerryhotton
 */

@Mapper
public interface ISCSkuActivityDao {

    SCSkuActivity querySCSkuActivity(SCSkuActivity scSkuActivity);

}

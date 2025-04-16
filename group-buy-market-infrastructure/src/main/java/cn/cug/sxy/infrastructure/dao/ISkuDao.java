package cn.cug.sxy.infrastructure.dao;

import cn.cug.sxy.infrastructure.dao.po.Sku;
import org.apache.ibatis.annotations.Mapper;

/**
 * @version 1.0
 * @Date 2025/3/28 17:07
 * @Description 商品信息 Dap
 * @Author jerryhotton
 */

@Mapper
public interface ISkuDao {

    Sku querySkuByGoodsId(String goodsId);

}

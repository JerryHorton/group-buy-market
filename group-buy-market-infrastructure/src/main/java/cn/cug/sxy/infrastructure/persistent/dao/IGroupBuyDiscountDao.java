package cn.cug.sxy.infrastructure.persistent.dao;

import cn.cug.sxy.infrastructure.persistent.po.GroupBuyDiscount;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @version 1.0
 * @Date 2025/3/27 14:42
 * @Description 折扣配置 Dao
 * @Author jerryhotton
 */

@Mapper
public interface IGroupBuyDiscountDao {

    List<GroupBuyDiscount> queryGroupBuyDiscount();

    GroupBuyDiscount queryGroupBuyDiscountByDiscountId(String discountId);

}

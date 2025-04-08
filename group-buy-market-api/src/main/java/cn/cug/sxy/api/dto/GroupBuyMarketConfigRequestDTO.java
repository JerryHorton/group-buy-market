package cn.cug.sxy.api.dto;

import lombok.Data;

/**
 * @version 1.0
 * @Date 2025/4/7 11:29
 * @Description 拼团营销配置查询请求对象
 * @Author jerryhotton
 */

@Data
public class GroupBuyMarketConfigRequestDTO {

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 商品ID
     */
    private String goodsId;
    /**
     * 来源
     */
    private String source;
    /**
     * 渠道
     */
    private String channel;

}

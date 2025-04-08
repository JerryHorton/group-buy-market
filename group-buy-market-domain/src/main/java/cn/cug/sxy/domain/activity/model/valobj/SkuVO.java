package cn.cug.sxy.domain.activity.model.valobj;

import lombok.*;

import java.math.BigDecimal;

/**
 * @version 1.0
 * @Date 2025/3/28 17:12
 * @Description 商品信息值对象
 * @Author jerryhotton
 */

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SkuVO {

    /**
     * 商品ID
     */
    private String goodsId;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 原始价格
     */
    private BigDecimal originalPrice;

}

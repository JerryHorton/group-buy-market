package cn.cug.sxy.api;

import cn.cug.sxy.api.dto.GroupBuyMarketConfigRequestDTO;
import cn.cug.sxy.api.dto.GroupBuyMarketConfigResponseDTO;
import cn.cug.sxy.api.response.Response;

/**
 * @version 1.0
 * @Date 2025/4/7 11:26
 * @Description 拼团营销配置 外部接口
 * @Author jerryhotton
 */

public interface IMarketIndexService {

    Response<GroupBuyMarketConfigResponseDTO> queryGroupBuyMarketConfig(GroupBuyMarketConfigRequestDTO requestDTO);

}

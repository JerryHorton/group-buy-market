package cn.cug.sxy.api;

import cn.cug.sxy.api.dto.LockMarketPayOrderRequestDTO;
import cn.cug.sxy.api.dto.LockMarketPayOrderResponseDTO;
import cn.cug.sxy.api.response.Response;

/**
 * @version 1.0
 * @Date 2025/3/31 21:42
 * @Description 拼团交易 外部接口
 * @Author jerryhotton
 */

public interface IMarketTradeService {

    Response<LockMarketPayOrderResponseDTO> lockMarketPayOrder(LockMarketPayOrderRequestDTO lockMarketPayOrderRequestDTO);

}

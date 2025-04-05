package cn.cug.sxy.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @version 1.0
 * @Date 2025/4/5 14:54
 * @Description 回调请求对象
 * @Author Sxy
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotifyRequestDTO {

    /**
     * 组队ID
     */
    private String teamId;
    /**
     * 外部单号
     */
    private List<String> outTradeNoList;

}

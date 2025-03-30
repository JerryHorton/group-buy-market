package cn.cug.sxy.domain.activity.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @Date 2025/3/30 16:40
 * @Description 活动人群标签作用域范围 枚举
 * @Author jerryhotton
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TagScopeVO {

    VISIBLE(true,false, "VISIBLE", "是否可看见拼团"),
    ENABLE(true, false,"ENABLE", "是否可参与拼团"),
    ;

    private Boolean allow;
    private Boolean refuse;
    private String code;
    private String desc;

}

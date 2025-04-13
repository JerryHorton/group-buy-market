package cn.cug.sxy.types.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @version 1.0
 * @Date 2025/4/10 11:42
 * @Description 基础事件
 * @Author jerryhotton
 */

@Data
public abstract class BaseEvent<T> {

    protected abstract EventMessage<T> buildEventMessage(T data);

    protected abstract String topic();

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EventMessage<T> {
        private String id;
        private Date timestamp;
        private T data;
    }

}

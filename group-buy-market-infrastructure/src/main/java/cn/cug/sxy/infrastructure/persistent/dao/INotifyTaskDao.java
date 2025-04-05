package cn.cug.sxy.infrastructure.persistent.dao;

import cn.cug.sxy.infrastructure.persistent.dao.po.NotifyTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @version 1.0
 * @Date 2025/4/3 11:39
 * @Description 通知回调任务 Dao
 * @Author Sxy
 */

@Mapper
public interface INotifyTaskDao {

    void insertNotifyTask(NotifyTask notifyTask);

    List<NotifyTask> queryUnExecuteSuccessNotifyTaskList(String teamId);

    int updateNotifyTaskStatus(NotifyTask notifyTaskReq);

}

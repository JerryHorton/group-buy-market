package cn.cug.sxy.domain.tag.service;

import cn.cug.sxy.domain.tag.model.entity.CrowdTagsJobEntity;
import cn.cug.sxy.domain.tag.repository.ITagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @Date 2025/3/30 10:35
 * @Description 人群标签 服务实现
 * @Author jerryhotton
 */

@Slf4j
@Service
public class TagService implements ITagService {

    @Resource
    private ITagRepository tagRepository;

    @Override
    public void execTagBatchJob(String tagId, String batchId) {
        log.info("人群标签批次任务 tagId:{} batchId:{}", tagId, batchId);
        // 1. 查询批次任务
        CrowdTagsJobEntity crowdTagsJobEntity = tagRepository.queryCrowdTagsJob(tagId, batchId);
        // 2. 采集用户数据
        // todo这部分需要采集用户的消费类数据，后续有用户发起拼单后再处理。
        // 3. 数据写入记录
        List<String> userIdList = new ArrayList<String>() {{
            add("oY_JN6pbmrEcLSj83m8r35fgn4Co");
        }};
        // 4. 添加人群标签明细
        for (String userId : userIdList) {
            tagRepository.addCrowdTagsDetail(tagId, userId);
        }
        // 5. 更新人群标签统计量
        tagRepository.updateCrowdTagsStatistics(tagId, userIdList.size());
    }

}

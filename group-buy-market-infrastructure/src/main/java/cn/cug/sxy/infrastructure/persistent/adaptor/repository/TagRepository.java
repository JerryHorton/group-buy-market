package cn.cug.sxy.infrastructure.persistent.adaptor.repository;

import cn.cug.sxy.domain.tag.model.entity.CrowdTagsJobEntity;
import cn.cug.sxy.domain.tag.repository.ITagRepository;
import cn.cug.sxy.infrastructure.persistent.dao.ICrowdTagsDao;
import cn.cug.sxy.infrastructure.persistent.dao.ICrowdTagsDetailDao;
import cn.cug.sxy.infrastructure.persistent.dao.ICrowdTagsJobDao;
import cn.cug.sxy.infrastructure.persistent.dao.po.CrowdTags;
import cn.cug.sxy.infrastructure.persistent.dao.po.CrowdTagsDetail;
import cn.cug.sxy.infrastructure.persistent.dao.po.CrowdTagsJob;
import cn.cug.sxy.infrastructure.persistent.redis.IRedisService;
import org.redisson.api.RBitSet;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @Date 2025/3/30 10:48
 * @Description 人群标签 仓储实现
 * @Author jerryhotton
 */

@Repository
public class TagRepository implements ITagRepository {

    @Resource
    private IRedisService redisService;

    @Resource
    private ICrowdTagsDao crowdTagsDao;

    @Resource
    private ICrowdTagsDetailDao crowdTagsDetailDao;

    @Resource
    private ICrowdTagsJobDao crowdTagsJobDao;


    @Override
    public CrowdTagsJobEntity queryCrowdTagsJob(String tagId, String batchId) {
        CrowdTagsJob crowdTagsJobReq = new CrowdTagsJob();
        crowdTagsJobReq.setTagId(tagId);
        crowdTagsJobReq.setBatchId(batchId);
        CrowdTagsJob crowdTagsJobRes = crowdTagsJobDao.queryCrowdTagsJob(crowdTagsJobReq);
        return CrowdTagsJobEntity.builder()
                .tagType(crowdTagsJobRes.getTagType())
                .tagRule(crowdTagsJobRes.getTagRule())
                .statStartTime(crowdTagsJobRes.getStatStartTime())
                .statEndTime(crowdTagsJobRes.getStatEndTime())
                .build();
    }

    @Override
    public void addCrowdTagsDetail(String tagId, String userId) {
        CrowdTagsDetail crowdTagsDetail = new CrowdTagsDetail();
        crowdTagsDetail.setTagId(tagId);
        crowdTagsDetail.setUserId(userId);
        try {
            crowdTagsDetailDao.insertCrowdTagsDetail(crowdTagsDetail);
            RBitSet bitSet = redisService.getBitSet(tagId);
            bitSet.set(redisService.getIndexFromUserId(userId));

        } catch (DuplicateKeyException ignore) {

        }

    }

    @Override
    public void updateCrowdTagsStatistics(String tagId, int size) {
        CrowdTags crowdTags = new CrowdTags();
        crowdTags.setTagId(tagId);
        crowdTags.setStatistics(size);
        crowdTagsDao.updateCrowdTagsStatistics(crowdTags);
    }

}

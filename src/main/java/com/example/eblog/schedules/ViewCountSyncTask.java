package com.example.eblog.schedules;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.eblog.entity.Post;
import com.example.eblog.service.PostService;
import com.example.eblog.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author zwp
 * @create 2020-05-22 22:13
 * @deprecated 定时器：定时刷新阅读量
 */
@Component
public class ViewCountSyncTask {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    PostService postService;

    @Scheduled(cron = "0 0/1 * * * *")// 0 0/1 * * * * 每分钟同步  0/0.5 * * * * * 每5秒
    public void  task() {
        Set<String> keys = redisTemplate.keys("rank:post:*");
        // 所有需要更新的id
        List<String> ids = new ArrayList<>();
        for (String key : keys) {
            // 判断key是否存在，如果存在添加进ids
            if(redisUtil.hHasKey(key, "post:viewCount")){
                ids.add(key.substring("rank:post:".length()));
            }
        }
        if(ids.isEmpty()) return;
        // 需要更新阅读量
        List<Post> posts = postService.list(new QueryWrapper<Post>().in("id", ids));
        posts.stream().forEach((post) ->{
            // 从模板引擎获取阅读量
            Integer viewCount = (Integer) redisUtil.hget("rank:post:" + post.getId(), "post:viewCount");
            post.setViewCount(viewCount);
        });
        if(posts.isEmpty()) return;
        // 批量更新
        boolean isSucc = postService.updateBatchById(posts);
        // 如果同步成功，就删除旧数据
        if(isSucc) {
            ids.stream().forEach((id) -> {
                redisUtil.hdel("rank:post:" + id, "post:viewCount");
                System.out.println(id + "---------------------->同步成功");
            });
        }
    }
}

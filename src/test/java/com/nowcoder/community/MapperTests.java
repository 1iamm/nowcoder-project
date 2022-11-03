package com.nowcoder.community;

import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MapperTests {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Test
    public void testSelectUser() {
        System.out.println(userMapper.selectByName("guanyu"));
        System.out.println(userMapper.selectByEmail("nowcoder1@sina.com"));
        System.out.println(userMapper.selectById(111));
    }

    @Test
    public void testUpdateUser() {
        System.out.println(userMapper.updateStatus(21, 0));
        userMapper.updatePassword(1, "SYSTEMOUT");
        userMapper.updateHeaderUrl(11, "http://images.nowcoder.com/head/12t.png");
    }

    @Test
    public void testInsertUser() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("123123");
        user.setSalt("abc");
        user.setEmail("abc@qq.com");
        user.setHeaderUrl("www.baidu.com");
        user.setCreateTime(new Date());
        System.out.println(userMapper.insertUser(user));
        System.out.println(user.getId());
    }

    @Test
    public void testSelectPosts() {
        List<DiscussPost> list = discussPostMapper.selectDiscussPosts(0, 0, 10);
        for (DiscussPost discussPost : list)
            System.out.println(discussPost);
        System.out.println(discussPostMapper.selectDiscussPostRows(0));
    }
}

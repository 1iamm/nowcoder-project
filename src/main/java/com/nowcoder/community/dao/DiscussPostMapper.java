package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

//    分页产生数据，所以应该返回帖子的集合
//      userId是之后查询个人主页的帖子时能用到，userId在sql语句中不是每次都用
//    offset是每一页起始行的行号，limit是本页最多显示多少条数据
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

//    查询出表里一共多少条数据，为了之后计算最多存在多少页
//    @Param("userId")可以给参数起别名，当参数比较长的时候有用
//    当需要动态拼一个条件并且参数中只有这一个条件时，必须起别名！！！
    int selectDiscussPostRows(@Param("userId") int userId);
}

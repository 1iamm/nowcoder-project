package com.nowcoder.community.dao;

import org.springframework.stereotype.Repository;

@Repository("alphaHibernate")  //字符串是给这个bean起的名，如果不加字符串的话默认是类名（首字母小写）
public class AlphaDaoHibernateImpl implements AlphaDao{
    @Override
    public String select() {
        return "HibernateImpl";
    }
}

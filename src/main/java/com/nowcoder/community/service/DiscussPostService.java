package com.nowcoder.community.service;

import com.nowcoder.community.dao.DiscussPostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussPostService {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    public List findDiscussPosts(int userId, int offset, int limit ,int orderMode){
        return discussPostMapper.selectDiscussPosts(userId,offset,limit,orderMode);
    }

    public int findDiscussPostRows(int userId){
        return discussPostMapper.selectDiscussPostRows(userId);
    }
}

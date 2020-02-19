package com.shanbei.server.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shanbei.dao.CategoryMapper;
import com.shanbei.dao.ChannelMapper;
import com.shanbei.domain.Category;
import com.shanbei.domain.Channel;
import com.shanbei.server.ChannelService;
@Service
public class ChannelServiceImpl implements ChannelService {
	@Resource
	private ChannelMapper channerMapper;
	
	@Resource
	private CategoryMapper categoryMapper;

	@Override
	public List<Channel> selects() {
		return channerMapper.selects();
	}

	@Override
	public List<Category> selectsByChannelId(Integer channelId) {
		// TODO Auto-generated method stub
		return categoryMapper.selectsByChannelId(channelId);
	}

}

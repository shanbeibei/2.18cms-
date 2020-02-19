package com.shanbei.server.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shanbei.dao.SlideMapper;
import com.shanbei.domain.Slide;
import com.shanbei.server.SlideService;
@Service
public class SlideServiceImpl implements SlideService {
	@Resource
	private SlideMapper slideMapper;

	@Override
	public List<Slide> selects() {
		// TODO Auto-generated method stub
		return slideMapper.selects();
	}

}

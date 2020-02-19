package com.shanbei.server.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shanbei.dao.TousuMapper;
import com.shanbei.domain.Tousubiao;
import com.shanbei.server.TousuService;
import com.shanbei.vo.ComplainVO;

/**
 * @于浩
 */
@Service
public class TousuServiceImpl implements TousuService {

	@Autowired
	private TousuMapper mapper;
	
	@Override
	public PageInfo<Tousubiao> list(int pageNum,ComplainVO vo) {
		PageHelper.startPage(pageNum, 3);
		List<Tousubiao> list=mapper.list(vo);
		PageInfo<Tousubiao> info=new PageInfo<Tousubiao>(list);
		return info;
	}

	@Override
	public List<Tousubiao> xiangqing() {
		
		return mapper.xiangqing();
	}

	@Override
	public void tousu(@Valid Tousubiao tousubiao) {
		mapper.tousu(tousubiao);
		
	}

}

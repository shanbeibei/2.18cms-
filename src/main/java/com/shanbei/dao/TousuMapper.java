package com.shanbei.dao;

import java.util.List;

import javax.validation.Valid;

import com.shanbei.domain.Tousubiao;
import com.shanbei.vo.ComplainVO;

/**
 * 
 */
public interface TousuMapper {

	//投诉列表
	List<Tousubiao> list(ComplainVO vo);
	//详情
	List<Tousubiao> xiangqing();
	void tousu(@Valid Tousubiao tousubiao);
}

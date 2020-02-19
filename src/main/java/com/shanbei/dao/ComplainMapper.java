package com.shanbei.dao;

import java.util.List;

import com.shanbei.domain.Complain;
import com.shanbei.vo.ComplainVO;

public interface ComplainMapper {
	
	int insert(Complain complain);
	
	//查询举报
	List<Complain> selects(ComplainVO complainVO);

}

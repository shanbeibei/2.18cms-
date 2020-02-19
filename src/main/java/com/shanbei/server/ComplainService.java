package com.shanbei.server;

import com.github.pagehelper.PageInfo;
import com.shanbei.domain.Complain;
import com.shanbei.vo.ComplainVO;

public interface ComplainService {
	//举报
	boolean insert(Complain complain);
	
	//查询举报
		PageInfo<Complain> selects(ComplainVO complainVO,Integer page,Integer pageSize);
}

package com.shanbei.server;

import com.github.pagehelper.PageInfo;
import com.shanbei.domain.User;

public interface UserService {

	User login(User user);
	
	/**
	 * 注册用户
	 * @Title: insertSelective 
	 * @Description: TODO
	 * @param record
	 * @return
	 * @return: int
	 */
	
	  int insertSelective(User record);
	
	/**
	 * 
	 * @Title: selects 
	 * @Description: 模糊查询
	 * @param name
	 * @param page
	 * @param pageSize
	 * @return
	 * @return: PageInfo<User>
	 */
	PageInfo<User> selects(String name,Integer page,Integer pageSize);
	/**
	 * 修改
	 * @Title: updateByPrimaryKeySelective 
	 * @Description: TODO
	 * @param record
	 * @return
	 * @return: int
	 */
	 int updateByPrimaryKeySelective(User record);

}

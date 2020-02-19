package com.shanbei.server;

import com.github.pagehelper.PageInfo;
import com.shanbei.domain.Comment;

public interface CommentService {
	/**
	 * 增加评论
	 * @Title: insert 
	 * @Description: TODO
	 * @param comment
	 * @return
	 * @return: int
	 */
	int insert(Comment comment);
	/**
	 * 
	 * @Title: selects 
	 * @Description: 查询评论
	 * @param comment
	 * @return
	 * @return: List<Comment>
	 */
	PageInfo<Comment> selects(Comment comment,Integer page,Integer pageSize);
	
}

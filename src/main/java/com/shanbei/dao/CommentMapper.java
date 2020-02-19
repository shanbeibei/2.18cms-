package com.shanbei.dao;

import java.util.List;

import com.shanbei.domain.Comment;

public interface CommentMapper {
	
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
	List<Comment> selects(Comment comment);

}

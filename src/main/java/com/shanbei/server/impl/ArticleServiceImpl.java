package com.shanbei.server.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shanbei.dao.ArticleMapper;
import com.shanbei.domain.Article;
import com.shanbei.domain.ArticleWithBLOBs;
import com.shanbei.server.ArticleService;
@Service
public class ArticleServiceImpl implements ArticleService {
	@Resource
	private ArticleMapper  articleMapper;

	@Override
	public PageInfo<Article> selects(Article article,Integer page,Integer pageSize) {
		
		PageHelper.startPage(page, pageSize);
		List<Article> list = articleMapper.selects(article);
		return new PageInfo<Article>(list);
	}

	@Override
	public int insertSelective(ArticleWithBLOBs record) {
		// TODO Auto-generated method stub
		return articleMapper.insertSelective(record);
	}

	@Override
	public ArticleWithBLOBs selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return articleMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ArticleWithBLOBs record) {
		
		return articleMapper.updateByPrimaryKeySelective(record);
	}

}

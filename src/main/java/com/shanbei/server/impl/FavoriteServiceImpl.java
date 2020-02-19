package com.shanbei.server.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shanbei.dao.FavoriteMapper;
import com.shanbei.domain.Favorite;
import com.shanbei.server.FavoriteService;
@Service
public class FavoriteServiceImpl implements FavoriteService{
@Autowired
private FavoriteMapper mapper;
	@Override
	public List<Favorite> select() {
		// TODO Auto-generated method stub
		return mapper.select();
	}
	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		mapper.delete(id);
	}
	@Override
	public void add(Favorite fa) {
		mapper.add(fa);
	}
	
}

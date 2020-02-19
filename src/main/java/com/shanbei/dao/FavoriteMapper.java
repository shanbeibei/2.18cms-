package com.shanbei.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shanbei.domain.Favorite;

public interface FavoriteMapper {
	List<Favorite> select();
	void delete(Integer id);
	void add(@Param("fa")Favorite fa);
}

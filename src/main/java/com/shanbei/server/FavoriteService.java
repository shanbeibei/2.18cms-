package com.shanbei.server;

import java.util.List;

import com.shanbei.domain.Favorite;

public interface FavoriteService {
	List<Favorite> select();
	void delete(Integer id);
	void add(Favorite fa);
}

package com.lance.service;

import com.lance.entity.NavigationEntity;

import java.util.List;

public interface NavigationService {
	/**
	 * 根据type查询菜单
	 * @param type
	 * @return
	 */
	List<NavigationEntity> findByType(int type);
}

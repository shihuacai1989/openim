package com.lance.repository;

import com.lance.entity.NavigationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NavigationRepository extends JpaRepository<NavigationEntity, Long>{
	
	/**
	 * 根据type查询菜单
	 * @param type
	 * @return
	 */
	List<NavigationEntity> findByTypeAndStatusIs(int type, int status);
}

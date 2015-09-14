package com.lance.repository;

import com.lance.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
	
	/**
	 * 根据userName查询
	 * @author lance
	 * 2014-6-11下午11:30:31
	 * @param userName
	 * @return
	 */
	UserEntity findByEmail(String email);
}

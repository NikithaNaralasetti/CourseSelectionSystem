package com.klef.jfsd.springboot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.klef.jfsd.springboot.model.Admin;

public interface AdminRepo extends JpaRepository<Admin, Long> {

	@Query("select admin from Admin admin where admin.id = ?1 And admin.password=?2")
	Admin IdAndPassword(Long id, String password);

}

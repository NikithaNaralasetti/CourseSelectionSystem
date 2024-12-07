package com.klef.jfsd.springboot.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.klef.jfsd.springboot.model.ProfilePicture;

public interface ProfileImageRepo extends JpaRepository<ProfilePicture, Long> {

}

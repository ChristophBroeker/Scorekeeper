package com.github.scorekeeper.persistence.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.github.scorekeeper.persistence.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

	List<User> findByName(String name);

}

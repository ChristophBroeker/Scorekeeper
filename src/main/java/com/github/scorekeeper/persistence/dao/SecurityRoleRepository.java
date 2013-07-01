package com.github.scorekeeper.persistence.dao;

import org.springframework.data.repository.CrudRepository;

import com.github.scorekeeper.persistence.entity.SecurityRole;

public interface SecurityRoleRepository extends CrudRepository<SecurityRole, Long> {

	SecurityRole findByName(String name);

}

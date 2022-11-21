package io.github.ruvesh.springsecurityclient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.ruvesh.springsecurityclient.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}

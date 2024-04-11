package com.example.demo.repository;

import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author perez
 */
public interface RepositoryUser extends JpaRepository<User,Long>{
    User findByEmail(@Param(("email")) String email);
}

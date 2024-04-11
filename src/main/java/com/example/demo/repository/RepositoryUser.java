package com.example.demo.repository;

import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author perez
 */
public interface RepositoryUser extends JpaRepository<User,Long>{

}

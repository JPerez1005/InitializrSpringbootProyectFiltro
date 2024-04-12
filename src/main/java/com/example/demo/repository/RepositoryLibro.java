package com.example.demo.repository;

import com.example.demo.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author perez
 */
@Repository
public interface RepositoryLibro extends JpaRepository<Libro,Long>{

}

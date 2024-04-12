package com.example.demo.repository;

import com.example.demo.models.Libro;
import com.example.demo.models.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author perez
 */
@Repository
public interface RepositoryPrestamo extends JpaRepository<Prestamo,Long> {

}

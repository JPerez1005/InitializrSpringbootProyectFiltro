package com.example.demo.service;

import java.util.List;
import java.util.Optional;

/**
 * @author perez
 */
public interface ServiceEntity<Dto, Long> {
    List<Dto> getAll();
    Optional<Dto> getById(Long id);
    Dto create(Dto dto);
    Dto update(Long id, Dto dto);
    void delete(Long id);
}

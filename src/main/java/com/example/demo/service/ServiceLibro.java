package com.example.demo.service;

import com.example.demo.dto.DtoLibro;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
/**
 * @author perez
 */
public interface ServiceLibro {

    List<DtoLibro> getAllLibros();
    
    Optional<DtoLibro> getLibroById(Long id);
    
    DtoLibro createLibro(DtoLibro dl) throws ParseException;
    
    DtoLibro updateLibro(Long id,DtoLibro dl) throws ParseException;
    
    void deleteLibro(Long id);
}

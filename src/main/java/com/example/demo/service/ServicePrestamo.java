package com.example.demo.service;

import com.example.demo.dto.DtoPrestamo;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

/**
 * @author perez
 */
public interface ServicePrestamo {

    List<DtoPrestamo> getAllPrestamos();
    
    Optional<DtoPrestamo> getPrestamoById(Long id);
    
    DtoPrestamo createPrestamo(DtoPrestamo dp,Long userId,Long libroId) throws ParseException;
    
    DtoPrestamo updatePrestamo(Long id,DtoPrestamo dp,Long userId,Long libroId) throws ParseException;
    
    void deletePrestamo(Long id);
}

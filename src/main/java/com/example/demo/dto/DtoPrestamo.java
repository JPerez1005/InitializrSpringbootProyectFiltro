package com.example.demo.dto;

import lombok.Data;

/**
 * @author perez
 */
@Data
public class DtoPrestamo {
    private Long id;
    
    private String fecha_prestamo;
    
    private String fecha_devolucion;
    
    private boolean aprobacion;
    
    private DtoUser usuario;
    
    private DtoLibro libro;
}

package com.example.demo.dto;

import java.util.Date;
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
    
    private DtoUser user;
    
    private DtoLibro libro;
}

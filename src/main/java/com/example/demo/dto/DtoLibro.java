package com.example.demo.dto;

import lombok.Data;

/**
 * @author perez
 */

@Data
public class DtoLibro {
    private Long id;
    
    private String titulo;
    
    private String autor;
    
    private String genero;
    
    private String fecha_publicacion;
    
    private int cantidad;
}

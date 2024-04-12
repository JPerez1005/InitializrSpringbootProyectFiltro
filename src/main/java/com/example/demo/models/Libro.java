package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author perez
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="libro")
public class Libro {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    private String titulo;
    
    private String autor;
    
    private String genero;
    
    private Date fecha_publicacion;
    
    private int cantidad;
    
    @OneToMany(mappedBy="libro")
    private List<Prestamo> prestamos;
    
}

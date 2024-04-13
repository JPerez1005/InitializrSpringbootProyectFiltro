package com.example.demo.mapper;

import com.example.demo.dto.DtoPrestamo;
import com.example.demo.models.Prestamo;
import com.example.demo.security.jwt.JwtFilter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author perez
 */
@Component
public class MapperPrestamo {
    private final ModelMapper modelMapper;
    
    @Autowired
    private JwtFilter jwtFilter;
    
    @Autowired
    public MapperPrestamo(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    
    public DtoPrestamo toDto(Prestamo p) {
        DtoPrestamo dp =modelMapper.map(p, DtoPrestamo.class);
        dp.setFecha_prestamo(obtenerFechaActual());
        if(jwtFilter.isUsuario()){
            dp.setAprobacion(false);
        }
        return dp;
    }

    public Prestamo toEntity(DtoPrestamo dp) {
        Prestamo p=modelMapper.map(dp, Prestamo.class);
        p.setFecha_prestamo(obtenerFechaActual2());
        if(jwtFilter.isUsuario()){
            p.setAprobacion(false);
        }
        return p;
    }
    
    public String obtenerFechaActual(){
        // Obtener la fecha y hora actual
        LocalDateTime fechaYHoraActual = LocalDateTime.now();
        // Formatear la fecha y hora actual como una cadena de texto
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fechaYHoraFormateada = fechaYHoraActual.format(formatter);
        return fechaYHoraFormateada;
    }
    
    public Date obtenerFechaActual2(){
        // Obtener la fecha y hora actual en milisegundos
        long milisegundos = System.currentTimeMillis();

        // Crear un objeto Date con la fecha y hora actual en milisegundos
        Date fechaActual = new Date(milisegundos);

        return fechaActual;
    }
}

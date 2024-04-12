package com.example.demo.mapper;

import com.example.demo.dto.DtoLibro;
import com.example.demo.models.Libro;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Component;

/**
 * @author perez
 */
@Component
public class MapperLibro {

    public String convertidorFechaString(Date fecha){
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String formatedFecha=sdf.format(fecha);
        return formatedFecha;
    }
    
    public Date convertidorFechaDate(String fecha) throws ParseException{
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        Date formatedFecha=sdf.parse(fecha);
        return formatedFecha;
    }
    
    public DtoLibro toDto(Libro l){
        DtoLibro dl=new DtoLibro();
        
        dl.setId(l.getId());
        dl.setAutor(l.getAutor());
        dl.setGenero(l.getGenero());
        dl.setTitulo(l.getTitulo());
        dl.setCantidad(l.getCantidad());
        dl.setFecha_publicacion(convertidorFechaString(l.getFecha_publicacion()));
        return dl;
    }
    
    public Libro toEntity(DtoLibro dl) throws ParseException{
        Libro l=new Libro();
        
        l.setId(dl.getId());
        l.setAutor(dl.getAutor());
        l.setGenero(dl.getGenero());
        l.setTitulo(dl.getTitulo());
        l.setCantidad(dl.getCantidad());
        l.setFecha_publicacion(convertidorFechaDate(dl.getFecha_publicacion()));
        return l;
    }
    
}

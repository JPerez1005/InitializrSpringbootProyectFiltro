package com.example.demo.mapper;

import com.example.demo.dto.DtoLibro;
import com.example.demo.dto.DtoPrestamo;
import com.example.demo.dto.DtoUser;
import com.example.demo.models.Libro;
import com.example.demo.models.Prestamo;
import com.example.demo.models.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Component;

/**
 * @author perez
 */

@Component
public class MapperPrestamo {
    
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
    
    public DtoPrestamo toDto(Prestamo p){
        DtoPrestamo dp=new DtoPrestamo();
        
        dp.setId(p.getId());
        dp.setAprobacion(p.isAprobacion());
        dp.setFecha_prestamo(convertidorFechaString(p.getFecha_prestamo()));
        dp.setFecha_devolucion(convertidorFechaString(p.getFecha_devolucion()));
        
        if(p.getUsuario()!=null){
            DtoUser du=new DtoUser();
            User u=p.getUsuario();
            
            du.setId(u.getId());
            du.setNombre(u.getNombre());
            du.setEmail(u.getEmail());
            du.setPassword(u.getPassword());
            du.setRole(u.getRole());
            du.setStatus(u.getStatus());
            du.setFecha(convertidorFechaString(u.getFecha_nacimiento()));
            
            dp.setUser(du);
        }
        
        if(p.getLibro()!=null){
            DtoLibro dl=new DtoLibro();
            Libro l=p.getLibro();
            dl.setId(l.getId());
            dl.setAutor(l.getAutor());
            dl.setGenero(l.getGenero());
            dl.setTitulo(l.getTitulo());
            dl.setCantidad(l.getCantidad());
            dl.setFecha_publicacion(convertidorFechaString(l.getFecha_publicacion()));
            
            dp.setLibro(dl);
        }
        return dp;
    }
    
    public Prestamo toEntity(DtoPrestamo dp) throws ParseException{
        Prestamo p=new Prestamo();
        
        p.setId(p.getId());
        p.setAprobacion(p.isAprobacion());
        p.setFecha_prestamo(convertidorFechaDate(dp.getFecha_prestamo()));
        p.setFecha_devolucion(convertidorFechaDate(dp.getFecha_devolucion()));
        
        if(dp.getUser()!=null){
            DtoUser du=dp.getUser();
            User u=new User();
            
            u.setId(du.getId());
            
            p.setUsuario(u);
        }
        
        if(dp.getLibro()!=null){
            DtoLibro dl=dp.getLibro();
            Libro l=new Libro();
            
            l.setId(dl.getId());
            
            p.setLibro(l);
        }
        return p;
    }
    
}

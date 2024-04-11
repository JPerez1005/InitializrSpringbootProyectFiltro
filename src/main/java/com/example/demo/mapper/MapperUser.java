package com.example.demo.mapper;

import com.example.demo.dto.DtoUser;
import com.example.demo.models.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Component;

/**
 * @author perez
 * model,dto,mapper,service,serviceImpl,controller
 */

@Component
public class MapperUser {
    
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
    
    public DtoUser toDto(User u){
        DtoUser dtoU=new DtoUser();
        
        //mandamos datos
        dtoU.setId(u.getId());
        dtoU.setNombre(u.getNombre());
        dtoU.setEmail(u.getEmail());
        dtoU.setPassword(u.getPassword());
        dtoU.setRole(u.getRole());
        dtoU.setStatus(u.getStatus());
        dtoU.setFecha(convertidorFechaString(u.getFecha_nacimiento()));
        
        return dtoU;
    }
    
    public User toEntity(DtoUser dtoU) throws ParseException{
        User u=new User();
        
        //mandamos datos
        u.setId(dtoU.getId());
        u.setNombre(dtoU.getNombre());
        u.setEmail(dtoU.getEmail());
        u.setPassword(dtoU.getPassword());
        u.setRole(dtoU.getRole());
        u.setStatus(dtoU.getStatus());
        u.setFecha_nacimiento(convertidorFechaDate(dtoU.getFecha()));
        
        return u;
    }

}

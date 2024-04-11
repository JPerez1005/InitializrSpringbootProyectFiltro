package com.example.demo.serviceImpl;

import com.example.demo.dto.DtoUser;
import com.example.demo.mapper.MapperUser;
import com.example.demo.models.User;
import com.example.demo.repository.RepositoryUser;
import com.example.demo.service.ServiceUser;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author perez
 * model,repository,dto,mapper,service,serviceImpl,controller
 */

@Service
public class ServiceImplUser implements ServiceUser{

    //datos User
    @Autowired
    private RepositoryUser ru;
    
    @Autowired
    private MapperUser mu;
    
    @Override
    public List<DtoUser> listaUsuarios() {
        List<User> usuarios=ru.findAll();/*recogemos los datos en entidades*/
        return usuarios.stream()/*Recorremos datos*/
                .map(mu::toDto)/*Lo cambiamos en Dto*/
                .collect(Collectors.toList());/*Lo recolectamos en una lista*/
    }

    @Override
    public Optional<DtoUser> getUserById(Long id) {
        Optional<User> userOptional=ru.findById(id);
        return userOptional.map(mu::toDto);
    }

    @Override
    public User createUser(DtoUser dtoUser) throws ParseException{
        //cambiamos datos de dto a entidad
        User u=mu.toEntity(dtoUser);
        /*Guardamos la cita*/
        return ru.save(u);
        
    }

    @Override
    public DtoUser updateUser(Long id, DtoUser dtoUser) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteUser(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}

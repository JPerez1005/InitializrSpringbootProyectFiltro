package com.example.demo.service;

import com.example.demo.dto.DtoUser;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

/**
 * @author perez
 * model,repository,dto,mapper,service,serviceImpl,controller
 */
public interface ServiceUser {

    ResponseEntity<String> ingresar(Map<String, String> requestMap);

    List<DtoUser> listaUsuarios();
    
    Optional<DtoUser> getUserById(Long id);
    
    DtoUser createUser(DtoUser dtoUser) throws ParseException;
    
    DtoUser updateUser(Long id,DtoUser dtoUser);
    
    void deleteUser(Long id);
    
}

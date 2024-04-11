package com.example.demo.controllers;

import com.example.demo.dto.DtoUser;
import com.example.demo.mapper.MapperUser;
import com.example.demo.models.User;
import com.example.demo.repository.RepositoryUser;
import com.example.demo.service.ServiceUser;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author perez
 */

@RestController
@RequestMapping("/user")
public class ControllerUser {

    //datos usuario
    @Autowired
    private ServiceUser su;
    
    @Autowired
    private RepositoryUser ru;
    
    @Autowired
    private MapperUser mu;
    
    @GetMapping("/listar_usuarios")
    public ResponseEntity<List<DtoUser>> listarUsuarios(){
        List<DtoUser> usuarios=su.listaUsuarios();
        return new ResponseEntity<>(usuarios,HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DtoUser> listarUsuarioPorId(@PathVariable Long id){
        Optional<DtoUser> DtoUserOptional=su.getUserById(id);
        return DtoUserOptional.map(user->new ResponseEntity<>(user,HttpStatus.OK))
                .orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping("/agregar_usuario")
    public ResponseEntity<DtoUser> guardarUsuario
        (@RequestBody DtoUser dtoU) throws ParseException{
        User newUser=su.createUser(dtoU);
        
        if(newUser==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        DtoUser newUserDto=mu.toDto(newUser);//Aquí lo convertimos a Dto
        return new ResponseEntity<>(dtoU,HttpStatus.CREATED);
    }
    
}

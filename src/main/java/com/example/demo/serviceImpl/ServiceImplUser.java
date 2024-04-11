package com.example.demo.serviceImpl;

import com.example.demo.dto.DtoUser;
import com.example.demo.mapper.MapperUser;
import com.example.demo.models.User;
import com.example.demo.repository.RepositoryUser;
import com.example.demo.security.CustomerDetailsService;
import com.example.demo.security.jwt.JwtUtil;
import com.example.demo.service.ServiceUser;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private CustomerDetailsService detallesPersona;
    
    @Autowired
    private JwtUtil jwtUtil;
    
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

    @Override
    public ResponseEntity<String> ingresar(Map<String, String> requestMap) {
        try {
            Authentication authentication=authenticationManager.authenticate(
                /*autenticamos los datos que nos entregan*/
                new UsernamePasswordAuthenticationToken(requestMap.get("email"),requestMap.get("password"))
            );
            //si se está autenticado...
            if (authentication.isAuthenticated()) {
                if(detallesPersona.getPersonaDetail().getStatus().equalsIgnoreCase("true")){
                    return new ResponseEntity<String>
                        ("{\"token\":\""+jwtUtil.generateToken
                            (detallesPersona.getPersonaDetail().getEmail(),
                            detallesPersona.getPersonaDetail().getRole())
                            +"\"}",HttpStatus.OK);
                }else{
//                    AprobacionAdmin exception=new AprobacionAdmin("","{\"mensaje\":\""
//                            +"Espere la Aprobación del admin"+"\"}",HttpStatus.BAD_REQUEST);
//                    throw exception;
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
//        ErrorValidacion exception=new ErrorValidacion("","{\"mensaje\":\""+"Credenciales Incorrectas"+"\"}"
//                ,HttpStatus.BAD_REQUEST);
//                    throw exception;
        return null;
    }

}

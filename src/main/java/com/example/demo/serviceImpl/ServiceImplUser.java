package com.example.demo.serviceImpl;

import com.example.demo.dto.DtoUser;
import com.example.demo.mapper.MapperUser;
import com.example.demo.models.User;
import com.example.demo.repository.RepositoryUser;
import com.example.demo.security.CustomerDetailsService;
import com.example.demo.security.jwt.JwtFilter;
import com.example.demo.security.jwt.JwtUtil;
import com.example.demo.service.ServiceUser;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    @Autowired
    private JwtFilter jwtFilter;
    
    //datos User
    @Autowired
    private RepositoryUser ru;
    
    @Autowired
    private MapperUser mu;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private CustomerDetailsService detallesPersona;
    
    @Autowired private JwtUtil jwtUtil;
    
    @Autowired private UniversalServiceImpl usi;
    
    @Override
    public List<DtoUser> listaUsuarios() {
        if(jwtFilter.isAdministrador()){
            return usi.getAll(ru, DtoUser.class);
        }
        return null;
    }

    @Override
    public Optional<DtoUser> getUserById(Long id) {
        if(jwtFilter.isAdministrador()){
            return usi.findById(ru, DtoUser.class, id);
        }
        return null;
    }

    @Override
    public DtoUser createUser(DtoUser du) throws ParseException{
        return usi.save(ru, du,User.class);
    }

    @Override
    public DtoUser updateUser(Long id, DtoUser du) {
        if(jwtFilter.isAdministrador()){
            Optional<User> optionalUser=ru.findById(id);
            if(optionalUser.isPresent()){
                User u=optionalUser.get();
                u=mu.toEntity(du);
                u=ru.save(u);
                return mu.toDto(u);
            }
        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        if(jwtFilter.isAdministrador()){
            ru.deleteById(id);
        }
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

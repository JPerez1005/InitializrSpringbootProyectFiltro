package com.example.demo.security;

import com.example.demo.repository.RepositoryUser;
import com.example.demo.models.User;
import java.util.ArrayList;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author perez
 */

@Slf4j
@Service
public class CustomerDetailsService implements UserDetailsService {

    @Autowired
    private RepositoryUser rp;
    
    private User p;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        p=rp.findByEmail(username);
        
        if(!Objects.isNull(p)){
            return new org.springframework.security.core.userdetails.User
                (p.getEmail(),p.getPassword(),new ArrayList<>());
        }else{
            throw new UsernameNotFoundException("Persona no encontrada");
        }
    }
    
    public User getPersonaDetail(){
        return p;
    }

}

package com.example.demo.serviceImpl;

import com.example.demo.dto.DtoLibro;
import com.example.demo.mapper.MapperLibro;
import com.example.demo.models.Libro;
import com.example.demo.repository.RepositoryLibro;
import com.example.demo.security.jwt.JwtFilter;
import com.example.demo.service.ServiceLibro;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @author perez
 */
@Service
public class ServiceImplLibro implements ServiceLibro{

    @Autowired private JwtFilter jwtFilter;
    
    @Autowired private RepositoryLibro rl;
    
    @Autowired private MapperLibro ml;
    
    @Autowired private UniversalServiceImpl usi;
    
    @Override
    public List<DtoLibro> getAllLibros(){
        return usi.getAll(rl, DtoLibro.class);
    }

    @Override
    public Optional<DtoLibro> getLibroById(Long id) {
        return usi.findById(rl, DtoLibro.class, id);
    }

    @Override
    public DtoLibro createLibro(DtoLibro dl) throws ParseException{
        if(jwtFilter.isBibliotecario()|| jwtFilter.isAdministrador()){
            return usi.save(rl, dl, Libro.class);
        }
        return null;
    }

    @Override
    public DtoLibro updateLibro(Long id, DtoLibro dl) throws ParseException{
        if(jwtFilter.isBibliotecario()|| jwtFilter.isAdministrador()){
            Optional<Libro> optionalLibro=rl.findById(id);
            if(optionalLibro.isPresent()){
                Libro l=optionalLibro.get();
                l=ml.toEntity(dl);
                l=rl.save(l);
                return ml.toDto(l);
            }
        }
        return null;
    }

    @Override
    public void deleteLibro(Long id) {
        if(jwtFilter.isBibliotecario()|| jwtFilter.isAdministrador()){
            rl.deleteById(id);
        }
    }

}

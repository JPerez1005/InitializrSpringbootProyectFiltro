package com.example.demo.serviceImpl;

import com.example.demo.dto.DtoLibro;
import com.example.demo.dto.DtoPrestamo;
import com.example.demo.dto.DtoUser;
import com.example.demo.mapper.MapperLibro;
import com.example.demo.mapper.MapperPrestamo;
import com.example.demo.mapper.MapperUser;
import com.example.demo.models.Libro;
import com.example.demo.models.Prestamo;
import com.example.demo.models.User;
import com.example.demo.repository.RepositoryLibro;
import com.example.demo.repository.RepositoryPrestamo;
import com.example.demo.repository.RepositoryUser;
import com.example.demo.security.jwt.JwtFilter;
import com.example.demo.service.ServicePrestamo;
import jakarta.persistence.EntityNotFoundException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author perez
 */

@Service
public class ServiceImplPrestamo implements ServicePrestamo{

    @Autowired
    private JwtFilter jwtFilter;
    
    @Autowired
    private RepositoryPrestamo rp;
    
    @Autowired private MapperPrestamo mp;
    
    @Autowired private RepositoryUser ru;
    
    @Autowired private RepositoryLibro rl;
    
    @Autowired private MapperUser mu;
    
    @Autowired private MapperLibro ml;
    
    @Override
    public List<DtoPrestamo> getAllPrestamos() {
        if(jwtFilter.isBibliotecario()|| jwtFilter.isAdministrador()){
        List<Prestamo> prestamos=rp.findAll();
        return prestamos.stream()
                .map(mp::toDto)
                .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public Optional<DtoPrestamo> getPrestamoById(Long id) {
        if(jwtFilter.isBibliotecario()|| jwtFilter.isAdministrador()){
        Optional<Prestamo> optionalPrestamo=rp.findById(id);
        return optionalPrestamo.map(mp::toDto);
        }
        return null;
    }

    @Override
    public DtoPrestamo createPrestamo(DtoPrestamo dp,Long userId,Long libroId) throws ParseException {
        if(jwtFilter.isBibliotecario()|| jwtFilter.isAdministrador()){
            /*Datos del Dto*/
            DtoUser du=convertidorUser(userId)
                .orElseThrow(()->new EntityNotFoundException
                ("Usuario no encontrado"));

            DtoLibro dl=convertidorLibro(libroId)
                .orElseThrow(()->new EntityNotFoundException
                ("Libro no encontrado"));

            /*datos del entity*/
            User u=ru.findById(du.getId())
            .orElseThrow(()->new EntityNotFoundException
            ("Usuario no encontrado"));

            Libro l=rl.findById(dl.getId())
            .orElseThrow(()->new EntityNotFoundException
            ("Libro no encontrado"));

            Prestamo p=new Prestamo();
            p = mp.toEntity(dp);
            p.setLibro(l);
            p.setUsuario(u);
            p=rp.save(p);
            return mp.toDto(p);
        }
        System.out.println("Usted no es ni bibliotecario, ni administrador");
        return null;
    }
    
    public Optional<DtoUser> convertidorUser(Long id){
        Optional<User> oo=ru.findById(id);
        return oo.map(mu::toDto);
    }
    
    public Optional<DtoLibro> convertidorLibro(Long id){
        Optional<Libro> oo=rl.findById(id);
        return oo.map(ml::toDto);
    }

    @Override
    public DtoPrestamo updatePrestamo(Long id, DtoPrestamo dp) throws ParseException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deletePrestamo(Long id) {
        if(jwtFilter.isBibliotecario()|| jwtFilter.isAdministrador()){
        rp.deleteById(id);
        }
    }

}

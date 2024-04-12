package com.example.demo.serviceImpl;

import com.example.demo.dto.DtoLibro;
import com.example.demo.mapper.MapperLibro;
import com.example.demo.models.Libro;
import com.example.demo.repository.RepositoryLibro;
import com.example.demo.service.ServiceLibro;
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
public class ServiceImplLibro implements ServiceLibro{

    @Autowired
    private RepositoryLibro rl;
    
    @Autowired
    private MapperLibro ml;
    
    @Override
    public List<DtoLibro> getAllLibros() {
        List<Libro> libros=rl.findAll();
        return libros.stream()
                .map(ml::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DtoLibro> getLibroById(Long id) {
        Optional<Libro> optionalMedico=rl.findById(id);
        return optionalMedico.map(ml::toDto);
    }

    @Override
    public DtoLibro createLibro(DtoLibro dl) throws ParseException{
        Libro l;
        l = ml.toEntity(dl);
        l=rl.save(l);
        return ml.toDto(l);
    }

    @Override
    public DtoLibro updateLibro(Long id, DtoLibro dl) throws ParseException{
        Optional<Libro> optionalLibro=rl.findById(id);
        if(optionalLibro.isPresent()){
            Libro l=optionalLibro.get();
            l.setId(dl.getId());
            l.setAutor(dl.getAutor());
            l.setGenero(dl.getGenero());
            l.setTitulo(dl.getTitulo());
            l.setCantidad(dl.getCantidad());
            l.setFecha_publicacion(ml.convertidorFechaDate(dl.getFecha_publicacion()));
            
            l=rl.save(l);
            return ml.toDto(l);
        }
        return null;
    }

    @Override
    public void deleteLibro(Long id) {
        rl.deleteById(id);
    }

}
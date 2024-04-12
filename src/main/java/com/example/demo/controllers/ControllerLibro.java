package com.example.demo.controllers;

import com.example.demo.dto.DtoLibro;
import com.example.demo.service.ServiceLibro;
import java.text.ParseException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author perez
 */

@RestController
@RequestMapping("/libro")
public class ControllerLibro {

    @Autowired
    private ServiceLibro sl;
    
    @GetMapping("/listar_libros")
    public ResponseEntity<List<DtoLibro>> listarLibros(){
        List<DtoLibro> libros=sl.getAllLibros();
        return new ResponseEntity<>(libros, HttpStatus.OK);
    }
    
    @GetMapping("/buscar_libro/{id}")
    public ResponseEntity<DtoLibro> LibroPorId(@PathVariable Long id){
        return sl.getLibroById(id)
                .map(libro -> new ResponseEntity<>(libro,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping("/guardar_libro")
    public ResponseEntity<DtoLibro> guardarLibro(@RequestBody DtoLibro dl) throws ParseException{
        DtoLibro createdLibro=sl.createLibro(dl);
        return new ResponseEntity<>(createdLibro,HttpStatus.OK);
    }
    
    @PutMapping("/modificar_libro/{id}")
    public ResponseEntity<DtoLibro> actualizarLibro
        (@PathVariable Long id,@RequestBody DtoLibro dl) throws ParseException{
        
            DtoLibro updateLibro=sl.updateLibro(id, dl);
            
            if (updateLibro!=null) {
                return new ResponseEntity<>(updateLibro,HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }
        
    @DeleteMapping("/eliminar_libro/{id}")
    public ResponseEntity<Void> eliminarMedico(@PathVariable Long id){
        sl.deleteLibro(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

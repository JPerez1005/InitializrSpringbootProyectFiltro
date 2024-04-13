package com.example.demo.controllers;

import com.example.demo.dto.DtoPrestamo;
import com.example.demo.service.ServicePrestamo;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author perez
 */
@RestController
@RequestMapping("/prestamo")
public class ControllerPrestamo {

    @Autowired private ServicePrestamo sp;
    
    @PostMapping("/registrar_prestamo")
    public ResponseEntity<DtoPrestamo> guardarPrestamo(
            @RequestParam Long userId,
            @RequestParam Long libroId,
            @RequestBody DtoPrestamo dp
    ) throws ParseException{
        DtoPrestamo createdPrestamo=sp.createPrestamo(dp, userId, libroId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPrestamo);
    }
    
    @GetMapping("/listar_prestamos")
    public ResponseEntity<List<DtoPrestamo>> listarInmuebles(){
        List<DtoPrestamo> prestamos=sp.getAllPrestamos();
        return new ResponseEntity<>(prestamos,HttpStatus.OK);
    }
    
    @GetMapping("/buscar_prestamo/{id}")
    public ResponseEntity<DtoPrestamo> listarUsuarioPorId(@PathVariable Long id){
        Optional<DtoPrestamo> DtoUserOptional=sp.getPrestamoById(id);
        return DtoUserOptional.map(user->new ResponseEntity<>(user,HttpStatus.OK))
                .orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

package com.example.demo.mapper;

import com.example.demo.dto.DtoLibro;
import com.example.demo.models.Libro;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapperLibro {

    private final ModelMapper modelMapper;

    @Autowired
    public MapperLibro(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DtoLibro toDto(Libro libro) {
        return modelMapper.map(libro, DtoLibro.class);
    }

    public Libro toEntity(DtoLibro dtoLibro) {
        return modelMapper.map(dtoLibro, Libro.class);
    }
}
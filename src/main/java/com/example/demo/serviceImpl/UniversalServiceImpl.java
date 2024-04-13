package com.example.demo.serviceImpl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper; // Importa ModelMapper

@Service
public class UniversalServiceImpl {
     private final ModelMapper modelMapper;

    public UniversalServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <Entity, Dto> List<Dto> getAll(JpaRepository<Entity, ?> repository, Class<Dto> dtoClass) {
        List<Entity> entities = repository.findAll();
        return entities.stream()
                .map(entity -> modelMapper.map(entity, dtoClass))
                .collect(Collectors.toList());
    }
    
    public <Entity, Dto, ID> Optional<Dto> findById(JpaRepository<Entity, ID> repository, Class<Dto> dtoClass, ID id) {
        Optional<Entity> optionalEntity = repository.findById(id);
        return optionalEntity.map(entity -> modelMapper.map(entity, dtoClass));
    }
    
    public <Entity, Dto> Dto save(JpaRepository<Entity, ?> repository, Dto dto, Class<Entity> entityClass) {
        Entity entityToSave = modelMapper.map(dto, entityClass);
        Entity savedEntity = repository.save(entityToSave);
        return (Dto) modelMapper.map(savedEntity, dto.getClass());
    }
    
}

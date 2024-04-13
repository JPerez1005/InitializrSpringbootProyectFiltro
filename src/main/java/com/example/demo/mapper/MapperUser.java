package com.example.demo.mapper;

import com.example.demo.dto.DtoUser;
import com.example.demo.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author perez
 * model,dto,mapper,service,serviceImpl,controller
 */

@Component
public class MapperUser {
    
    private ModelMapper modelMapper;
    
    @Autowired
    public MapperUser(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DtoUser toDto(User u) {
        return modelMapper.map(u, DtoUser.class);
    }

    public User toEntity(DtoUser du) {
        return modelMapper.map(du, User.class);
    }
}

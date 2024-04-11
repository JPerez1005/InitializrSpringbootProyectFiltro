package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author perez
 * model,dto,mapper,service,serviceImpl,controller
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoUser {

    private Long id;
    private String nombre;
    private String email;
    private String password;
    private String fecha;
    private String role;
    private String status;
}

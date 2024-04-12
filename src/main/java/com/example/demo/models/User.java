package com.example.demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * @author perez
 * model,repository,dto,mapper,service,serviceImpl,controller
 */
@NamedQuery(name="User.findByEmail",query="select u from User u where u.email=:email")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate//solo actualiza las columnas que cambiaron
@DynamicInsert//solo inserta las columnas que no son nulas
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    
    private String email;
    
    private String password;
    
    private Date fecha_nacimiento;
    
    @Column(name="status")
    private String status;
    
    @Column(name="role")
    private String role;
    
    @OneToMany(mappedBy="usuario")
    private List<Prestamo> prestamos;
    
}

package com.shared_data.shared.models;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.shared_data.shared.models.dtos.UserDTO;

import lombok.Data;

@Data
@Table(name = "usuario")
public class User{
    
    @Id
    @Column("uId")
    private Long id;

    @Column("uNombre")
    private String nombre;

    @Column("uApellido")
    private String apellido;

    @Column("uAlias")
    private String alias;

    @Column("uPassword")
    private String contrasena;

    @Column("uActivo")
    private boolean activo;

    @Column("uCreacion")
    private LocalDateTime fechaCreacion;

    @Column("uModificacion")
    private LocalDateTime fechaActualizacion;

    @Transient
    private List<Rol> roles;


    public User() {
    }

    public User(String nombre, String apellido, String alias, String contrasena, boolean activo, LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion, List<Rol> roles) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.alias = alias;
        this.contrasena = contrasena;
        this.activo = activo;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
        this.roles = roles;
    }


    public User(UserDTO userDTO){
        this.activo = userDTO.isActivo();
        this.alias = userDTO.getAlias();
        this.apellido= userDTO.getApellido();
        this.contrasena = userDTO.getContrasena();
        this.nombre = userDTO.getNombre();
        this.roles = userDTO.getRoles().stream().map(Rol::new).toList();
    }

    public void update(User data){
        setActivo(data.isActivo());
        setAlias(data.getAlias());
        setNombre(data.getNombre());
        setApellido(data.getApellido());
    }

    public void update(UserDTO data){
        setActivo(data.isActivo());
        setAlias(data.getAlias());
        setNombre(data.getNombre());
        setApellido(data.getApellido());
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", apellido='" + getApellido() + "'" +
            ", alias='" + getAlias() + "'" +
            ", contrasena='" + getContrasena() + "'" +
            ", activo='" + isActivo() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", fechaActualizacion='" + getFechaActualizacion() + "'" +
            "}";
    }

}

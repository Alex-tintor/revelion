package com.shared_data.shared.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.shared_data.shared.models.enums.RoleEnum;

import lombok.Data;


@Data
@Table(name = "Rol")
public class Rol {
    
    @Id
    @Column("rUuid")
    private Long rUuid;

    @Column("rTipo")
    private RoleEnum tipoRol;

    public Rol() {
    }

    public Rol(Long rUuid, RoleEnum tipoRol) {
        this.rUuid = rUuid;
        this.tipoRol = tipoRol;
    }

    public Rol(RoleEnum tipoRol){
        this.tipoRol = tipoRol;
    }

    @Override
    public String toString() {
        return "{" +
            " rUuid='" + getRUuid() + "'" +
            ", tipoRol='" + getTipoRol() + "'" +
            "}";
    }

}

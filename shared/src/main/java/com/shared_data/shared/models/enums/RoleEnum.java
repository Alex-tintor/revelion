package com.shared_data.shared.models.enums;

public enum RoleEnum {

    USER("Usuario"),
    ADMIN("Administrador");

    private final String descripcion;

    private RoleEnum(String descripcion){
        this.descripcion = descripcion;
    }

    public String getDescipcion(){
        return this.descripcion;
    }

}
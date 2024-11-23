package com.users_service.users.services.ServicesInterfaces;

import com.shared_data.shared.models.Rol;
import com.shared_data.shared.models.User;
import com.shared_data.shared.models.enums.RoleEnum;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RolServiceInterface {

    public Mono<Rol> findRole(Long id);

    public Boolean isAdmin(User user);

    public Mono<Rol> findRoleBytipo(RoleEnum role);

    public Flux<Rol> findRolesByUserId(Long userId);
    
}
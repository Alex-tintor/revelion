package com.users_service.users.repositorys;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.shared_data.shared.models.Rol;
import com.shared_data.shared.models.enums.RoleEnum;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public interface RolRepository extends R2dbcRepository<Rol,Long>{

    public Mono<Rol> findByTipoRol(RoleEnum tipoRol);

    @Query("SELECT r.* FROM Rol r " +
       "JOIN UserRoles ur ON r.rUuid = ur.urRolId " +
       "WHERE ur.urUserId = 1")
    Flux<Rol> findRolesByUserId();

}

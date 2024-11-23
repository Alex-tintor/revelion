package com.users_service.users.services.ServicesImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shared_data.shared.models.Rol;
import com.shared_data.shared.models.User;
import com.shared_data.shared.models.enums.RoleEnum;
import com.users_service.users.repositorys.RolRepository;
import com.users_service.users.services.ServicesInterfaces.RolServiceInterface;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class RolServiceImplement implements RolServiceInterface {
    
        @Autowired
    private RolRepository rolRepository;

    @Override
    public Mono<Rol> findRole(Long id) {
        return rolRepository.findById(id).defaultIfEmpty(new Rol());
    }

    @Override
    public Boolean isAdmin(User user) {
        return user.getRoles().contains(findRole(1L));
    }

    @Override
    public Mono<Rol> findRoleBytipo(RoleEnum role) {
        return rolRepository.findByTipoRol(role).defaultIfEmpty(new Rol());
    }

    @Override
    public Flux<Rol> findRolesByUserId(Long userId) {
        log.warn("Inicio del método findRolesByUserId con userId: {}", userId);
    
        return rolRepository.findRolesByUserId()
                .doOnSubscribe(subscription -> log.warn("Se suscribió al flujo para buscar roles del usuario con ID: {}", userId))
                .doOnNext(rol -> log.warn("Rol encontrado: {}", rol.getTipoRol()))
                .doOnComplete(() -> log.warn("Finalizó la búsqueda de roles para userId: {}", userId))
                .doOnError(e -> log.warn("Error durante la búsqueda de roles para userId: {}", userId, e))
                .defaultIfEmpty(null)
                .doOnNext(rol -> {
                    if (rol == null) {
                        log.warn("No se encontraron roles para el usuario con ID: {}", userId);
                    }
                })
                .doOnTerminate(() -> log.warn("Método findRolesByUserId finalizó para userId: {}", userId));
    }
}

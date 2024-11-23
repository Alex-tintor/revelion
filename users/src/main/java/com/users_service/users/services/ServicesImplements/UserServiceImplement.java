package com.users_service.users.services.ServicesImplements;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.shaded.gson.Gson;
import com.shared_data.shared.models.User;
import com.shared_data.shared.models.UserRoles;
import com.shared_data.shared.models.dtos.UserDTO;
import com.shared_data.shared.utilities.ExcepcionPersonalizada;
import com.shared_data.shared.utilities.SecurityUtils;
import com.users_service.users.repositorys.UserRepository;
import com.users_service.users.repositorys.UserRolesRepository;
import com.users_service.users.services.ServicesInterfaces.UserServiceInterface;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImplement implements UserServiceInterface {

    @Autowired
    private UserRepository userRepository;

     @Autowired
    private RolServiceImplement rolService;

    @Autowired
    private UserRolesRepository userRolesRepository;

    @Override
    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Mono<User> findByAlias(String alias) {
        return userRepository.findByAlias(alias);
    }

    @Override
    public Mono<User> findById(Long userId) {
        return userRepository.findById(userId).defaultIfEmpty(new User());
    }

    public Mono<Long> createOrUpdate(Mono<User> user) {        
        return userRepository.save(user.block()).map(User::getId);

    }

    @Override
    public void update(User user) {
        Mono<User> userToUpdate = userRepository.findByAlias(user.getAlias());
        if (Objects.nonNull(userToUpdate)) {
            createOrUpdate(userToUpdate);
        } else {
            createOrUpdate(userToUpdate);
        }
    }

    @Override
    public void disable(Long uuid) {
        Mono<User> userToDisable = findById(uuid);
        if (Objects.nonNull(userToDisable)) {
            userToDisable.map(user -> {
                user.setActivo(false);
                return user;
            });
            createOrUpdate(userToDisable);
        }
    }

    @Override
    public Boolean validate(Long uuid) {
        return Objects.nonNull(findById(uuid));
    }
    
    @Override
    public Boolean validate(String alias) {
        return Objects.nonNull(findByAlias(alias));
    }
    
    @Override
    public Mono<User> findByToken(String auth) {
        String token = auth.split(" ")[1];
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        Gson json = new Gson();
        String data = json.fromJson(payload, String.class);
        String alias = data.valueOf("user_alias");
        return findByAlias(alias);

    }

    @Override
    public Boolean isAdmin(User user) {
        return rolService.isAdmin(user);
    }

    @Override
    public UserDTO getUserDto(User user) {
        return new UserDTO(user);
    }

 
    @Override
    public Mono<Long> createOrUpdate(User user) throws ExcepcionPersonalizada {
        user.setContrasena(SecurityUtils.encodePass(user.getContrasena()));
        user.setFechaActualizacion(LocalDateTime.now());
        user.setFechaCreacion(LocalDateTime.now());
    
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            return Mono.error(new ExcepcionPersonalizada("No es posible crear un usuario sin roles"));
        }
    
        Mono<Object> rsultMono = findByAlias(user.getAlias())
            .flatMap(existeUsuario -> Mono.error(new ExcepcionPersonalizada(
                "Alias ya existe, no es posible crear un usuario con un alias existente, por favor intente de nuevo con un alias distinto")))
            .switchIfEmpty(
                userRepository.save(user) // Guarda el usuario
                    .flatMap(usuarioCreado -> 
                        Flux.fromIterable(user.getRoles())
                            .flatMap(rol -> 
                                rolService.findRoleBytipo(rol.getTipoRol())
                                    .flatMap(rolEncontrado -> 
                                        userRolesRepository.save(new UserRoles(usuarioCreado.getId(),rolEncontrado.getRUuid())) 
                                    )
                            )
                            .then(Mono.just(user.getId())) 
                    )
            );
        return rsultMono.cast(Long.class);
    }
}

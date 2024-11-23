package com.users_service.users.services.ServicesInterfaces;

import com.shared_data.shared.models.User;
import com.shared_data.shared.models.dtos.UserDTO;
import com.shared_data.shared.utilities.ExcepcionPersonalizada;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserServiceInterface {

    public Flux<User> findAll();

    public Mono<User> findByAlias(String alias);

    public Mono<User> findById(Long userId);

    public void update(User user);

    public void disable(Long uuid);

    public Boolean validate(Long uuid);

    public Boolean validate(String alias);

    public Mono<User> findByToken(String token);

    public Boolean isAdmin(User user);

    public UserDTO getUserDto(User user);

    public Mono<Long> createOrUpdate(User user) throws ExcepcionPersonalizada;

}
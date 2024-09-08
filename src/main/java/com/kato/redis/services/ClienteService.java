package com.kato.redis.services;

import com.kato.redis.dto.ClienteDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClienteService {

    Flux<ClienteDTO> findAll();
    Mono<ClienteDTO> findById(String id);
    Mono<ClienteDTO> save(ClienteDTO clienteDTO);
    Mono<Void> deleteById(String id);
}

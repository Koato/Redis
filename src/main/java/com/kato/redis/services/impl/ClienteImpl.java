package com.kato.redis.services.impl;

import com.kato.redis.dto.ClienteDTO;
import com.kato.redis.repository.ClienteRepository;
import com.kato.redis.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClienteImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    @Cacheable(value = "findAll")
    public Flux<ClienteDTO> findAll() {
        return clienteRepository.findAll();
    }

    @Override
    @Cacheable(value = "findById", key = "#id", unless = "#result == null")
    public Mono<ClienteDTO> findById(String id) {
        return clienteRepository.findById(id);
    }

    @Override
    @Cacheable(value = "save", key = "#clienteDTO._id", unless = "#result == null")
    public Mono<ClienteDTO> save(ClienteDTO clienteDTO) {
        return clienteRepository.save(clienteDTO);
    }

    @Override
    @Cacheable(value = "deleteById", key = "#id")
    public Mono<Void> deleteById(String id) {
        return clienteRepository.deleteById(id);
    }
}

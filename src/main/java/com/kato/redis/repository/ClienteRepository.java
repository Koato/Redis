package com.kato.redis.repository;

import com.kato.redis.dto.ClienteDTO;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ClienteRepository extends ReactiveMongoRepository<ClienteDTO, String> {
}

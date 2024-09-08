package com.kato.redis.controller;

import com.kato.redis.dto.ClienteDTO;
import com.kato.redis.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private CacheManager cacheManager;

    @GetMapping
    public Mono<ResponseEntity<Flux<ClienteDTO>>> listarClientes() {
        return Mono.just(ResponseEntity.ok(clienteService.findAll()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ClienteDTO>> buscarClientePorId(@PathVariable String id) {
        return clienteService.findById(id).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/registrarCliente")
    public Mono<ResponseEntity<ClienteDTO>> registrarCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        return clienteService.save(clienteDTO).map(ResponseEntity::ok)
                .onErrorResume(r -> Mono.just(r).cast(WebExchangeBindException.class)
                            .flatMap(e -> Mono.just(e.getFieldErrors()))
                            .flatMapMany(Flux::fromIterable)
                            .map(fieldError -> "El campo: " + fieldError.getField() + " " + fieldError.getDefaultMessage())
                            .collectList()
                            .flatMap(l -> Mono.just(ResponseEntity.badRequest().build()))
                );
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ClienteDTO>> editarCliente(@Valid @RequestBody ClienteDTO clienteDTO, @PathVariable String id) {
        return clienteService.findById(id).flatMap(client -> {
           client.setNombre(clienteDTO.getNombre());
           client.setApellido(clienteDTO.getApellido());
           client.setEdad(clienteDTO.getEdad());
           client.setSueldo(clienteDTO.getSueldo());
           return clienteService.save(client);
        }).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> eliminarCliente(@PathVariable String id) {
        return clienteService.deleteById(id).thenReturn(ResponseEntity.ok().build());
    }

    @PatchMapping("/vaciarCache")
    public void evictCache() {
        cacheManager.getCacheNames().forEach(name -> cacheManager.getCache(name).clear());
    }
}

package com.kato.redis.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.UUID;

@Document(collection = "clientes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ClienteDTO implements Serializable {

    @Id
    private String _id = UUID.randomUUID().toString();

    @NotEmpty
    private String nombre;

    @NotEmpty
    private String apellido;

    @NotNull
    private Integer edad;

    @NotNull
    private Double sueldo;
    
}

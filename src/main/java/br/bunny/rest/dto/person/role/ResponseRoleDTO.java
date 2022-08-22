package br.bunny.rest.dto.person.role;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ResponseRoleDTO {

    private UUID id;
    private String name;
}

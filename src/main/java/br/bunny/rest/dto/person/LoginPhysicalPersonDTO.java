package br.bunny.rest.dto.person;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginPhysicalPersonDTO {

    private String email;
    private String password;
}

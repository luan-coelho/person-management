package br.bunny.rest.dto.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AuthRequest {

    @NotBlank(message = "Informe o email")
    @Email(message = "Informe um email válido")
    private String email;
    @NotBlank(message = "Informe a senha")
    private String password;
}

package br.bunny.rest.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder
@Getter
@Setter
public class AuthRequest {

    @NotBlank(message = "inform the email")
    @Email(message = "Please provide a valid email")
    private String email;
    @NotBlank(message = "inform the password")
    private String password;
}

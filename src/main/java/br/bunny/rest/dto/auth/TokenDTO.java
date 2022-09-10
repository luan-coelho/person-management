package br.bunny.rest.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TokenDTO {

    private String accessToken;
    private String refleshToken;
}

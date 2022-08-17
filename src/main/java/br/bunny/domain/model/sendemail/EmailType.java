package br.bunny.domain.model.sendemail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum EmailType {

    DEFAULT("default-message.html"),
    RESET_PASSWORD("reset-password.html");

    private String htmlFileName;
}

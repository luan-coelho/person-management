package br.bunny.dto.person;

import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class TelephoneDTO {

    @Pattern(regexp = "^(?:\\+55)?\\s?\\(?0?[1-9][1-9]\\)?\\s?(?:9)?\\s?\\d{4}\\-?\\d{4}$", message = "Phone number with invalid format")
    @Size(min = 9, max = 14, message = "The phone number must contain a maximum of 10 digits")
    private String number;
}

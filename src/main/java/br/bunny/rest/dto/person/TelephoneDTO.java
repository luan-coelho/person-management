package br.bunny.rest.dto.person;

import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class TelephoneDTO {

    @Pattern(regexp = "^\\(?[1-9]{2}\\)? ?(?:[2-8]|9[1-9])[0-9]{3}?[0-9]{4}$", message = "Phone number with invalid format")
    @Size(min = 9, max = 14, message = "The phone number must contain a maximum of 10 digits")
    private String number;
}

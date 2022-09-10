package br.bunny.rest.dto.person;

import br.bunny.domain.model.person.Gender;
import br.bunny.domain.model.person.Role;
import br.bunny.rest.dto.person.telephone.TelephoneDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePhysicalPersonDTO {

    @NotBlank(message = "Inform the name")
    private String name;
    @NotBlank(message = "Inform the surname")
    private String surname;
    @Email(message = "Please provide a valid email")
    private String email;
    @CPF(message = "Please provide a valid CPF")
    private String cpf;
    @NotBlank(message = "Enter a password")
    private String password;
    @NotBlank(message = "Confirm the Password")
    private String confirmPassword;
    @NotNull(message = "Inform the gender")
    private Gender gender;
    @Past(message = "The date of birth must be a day before today")
    private LocalDate birthday;
    @Valid
    private List<TelephoneDTO> phones;
    private List<Role> roles;
}

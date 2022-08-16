package br.bunny.rest.dto.person;

import br.bunny.domain.model.person.Gender;
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
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePhysicalPersonDTO {

    private UUID id;
    @NotBlank(message = "O campo nome não pode ser nulo")
    private String name;
    @NotBlank(message = "O campo de sobrenome não pode ser nulo")
    private String surname;
    @Email(message = "Informe um email válido")
    private String email;
    @CPF(message = "Informe um CPF válido")
    private String cpf;
    @NotBlank(message = "Informe uma senha")
    private String password;
    @NotNull(message = "Informe o sexo")
    private Gender gender;
    @Past(message = "A data de nascimento deve ser um dia anterior ao dia de hoje")
    private LocalDate birthday;
    @Valid
    private List<TelephoneDTO> phones;
    private boolean active;
}

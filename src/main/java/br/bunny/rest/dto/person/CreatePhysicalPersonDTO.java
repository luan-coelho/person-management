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

    private String name;
    @NotBlank(message = "O campo de sobrenome não pode ser nulo")
    private String surname;
    @Email(message = "Informe um email válido")
    private String email;
    @CPF(message = "Informe um CPF válido")
    private String cpf;
    @NotBlank(message = "Informe uma senha")
    private String password;
    @NotBlank(message = "Confirme a senha")
    private String confirmPassword;
    @NotNull(message = "Informe o sexo")
    private Gender gender;
    @Past(message = "A data de nascimento deve ser um dia anterior ao dia de hoje")
    private LocalDate birthday;
    @Valid
    private List<TelephoneDTO> phones;
    private List<Role> roles;
}

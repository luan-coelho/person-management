package br.bunny.dto.person;

import br.bunny.model.person.Gender;
import br.bunny.model.person.Phone;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalPersonDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id;
    @NotBlank(message = "O campo nome não pode ser nulo.")
    private String name;
    @NotBlank(message = "O campo de sobrenome não pode ser nulo.")
    private String surname;
    @CPF(message = "Informe um CPF válido")
    @Column(unique = true)
    private String cpf;
    @Email(message = "Informe um email válido.")
    private String email;
    @NotBlank(message = "Informe uma senha")
    private String password;
    @NotNull(message = "Informe o sexo")
    private Gender gender;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Past(message = "O campo de data de nascimento não pode ser nulo.")
    private LocalDate birthday;
    private List<Phone> phones;
    private boolean active;
}
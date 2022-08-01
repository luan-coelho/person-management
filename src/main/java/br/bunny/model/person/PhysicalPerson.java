package br.bunny.model.person;

import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serial;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class PhysicalPerson extends Person {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Informe o nome")
    private String name;

    @NotBlank(message = "Informe o sobrenome")
    private String surname;

    @NotNull(message = "Informe o sexo")
    private Gender gender;

    @CPF(message = "Informe um CPF válido")
    @Column(unique = true)
    private String cpf;

    @Past(message = "Informe uma data de nascimento anterior ao dia de hoje")
    private LocalDate birthday;
}

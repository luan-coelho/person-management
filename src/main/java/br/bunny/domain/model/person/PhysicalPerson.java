package br.bunny.domain.model.person;

import lombok.*;
import lombok.experimental.SuperBuilder;
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
@SuperBuilder
@Entity
public class PhysicalPerson extends Person {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Inform the name")
    private String name;

    @NotBlank(message = "Inform the surname")
    private String surname;

    @NotNull(message = "Inform the gender")
    private Gender gender;

    @CPF(message = "Please provide a valid CPF")
    @Column(unique = true)
    private String cpf;

    @Past(message = "Enter a date of birth earlier than today")
    private LocalDate birthday;
}

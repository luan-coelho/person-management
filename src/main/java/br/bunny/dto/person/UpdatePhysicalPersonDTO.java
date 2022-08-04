package br.bunny.dto.person;

import br.bunny.model.person.Gender;
import br.bunny.model.person.Phone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePhysicalPersonDTO {

    private UUID id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @Email
    private String email;
    private Gender gender;
    private LocalDate birthday;
    private List<Phone> phones;
}

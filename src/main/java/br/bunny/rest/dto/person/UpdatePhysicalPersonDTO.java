package br.bunny.rest.dto.person;

import br.bunny.domain.model.person.Gender;
import br.bunny.rest.dto.person.telephone.TelephoneDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
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
    @Valid
    private List<TelephoneDTO> phones;
}

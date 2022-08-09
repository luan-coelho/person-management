package br.bunny.dto.person;

import br.bunny.model.person.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePhysicalPersonDTO {

    private UUID id;
    private String name;
    private String surname;
    private String cpf;
    private String email;
    private Gender gender;
    private LocalDate birthday;
    private List<TelephoneDTO> phones;
    private boolean active;
}

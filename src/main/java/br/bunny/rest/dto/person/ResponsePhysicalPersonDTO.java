package br.bunny.rest.dto.person;

import br.bunny.domain.model.person.Gender;
import br.bunny.rest.dto.person.role.ResponseRoleDTO;
import br.bunny.rest.dto.person.telephone.TelephoneDTO;
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
    private List<ResponseRoleDTO> roles;
    private boolean active;
}

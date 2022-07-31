package br.bunny.dto;

import br.bunny.model.user.Phone;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id;
    @NotBlank(message = "O campo nome não pode ser nulo.")
    private String name;
    @NotBlank(message = "O campo de sobrenome não pode ser nulo.")
    private String surname;
    @JsonFormat(pattern="yyyy-MM-dd")
    @Past(message = "O campo de data de nascimento não pode ser nulo.")
    private LocalDate birthday;
    @Email(message = "Informe um email válido.")
    private String email;
    @NotBlank(message = "Informe uma senha")
    private String password;
    private List<Phone> phones;
    private boolean ativo;
}

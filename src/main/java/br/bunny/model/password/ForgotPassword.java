package br.bunny.model.password;

import br.bunny.model.DefaultEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.io.Serial;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ForgotPassword extends DefaultEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    private String code;
    private UUID idUser;
    private LocalDateTime dateTimeDeadline;
}

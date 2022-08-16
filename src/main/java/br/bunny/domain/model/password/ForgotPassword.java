package br.bunny.domain.model.password;

import br.bunny.domain.model.DefaultEntity;
import br.bunny.domain.model.person.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serial;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class ForgotPassword extends DefaultEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    private String code;
    @ManyToOne
    private Person person;
    private LocalDateTime dateTimeDeadline;
}

package br.bunny.model.person;

import br.bunny.model.DefaultEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Telephone extends DefaultEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    private String number;
}

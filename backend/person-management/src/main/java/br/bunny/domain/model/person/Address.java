package br.bunny.domain.model.person;

import br.bunny.domain.model.DefaultEntity;
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
public class Address extends DefaultEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    private String street;
    private String number;
    private String district;
    private String complement;
    private String zipCode;
}

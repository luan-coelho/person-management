package br.bunny.model.person;

import br.bunny.model.DefaultEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serial;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person extends DefaultEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    private String email;
    private String password;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "person_phone")
    private List<Telephone> phones;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Address address;
}

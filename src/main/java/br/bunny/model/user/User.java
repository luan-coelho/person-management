package br.bunny.model.user;

import br.bunny.model.DefaultEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serial;
import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_user")
@Entity
public class User extends DefaultEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "The name field cannot be null.")
    @Column(nullable = false, length = 45)
    private String name;
    @Column(nullable = false, length = 150)
    private String surname;
    @Column(nullable = false)
    private LocalDate birthday;
    @Column(unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_phone")
    private List<Phone> phones;
}

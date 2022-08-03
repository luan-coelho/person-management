package br.bunny.filter;

import br.bunny.model.person.Gender;
import br.bunny.model.person.PhysicalPerson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalPersonFilter extends FieldsFilter {

    private String name;
    private String surname;
    private String email;
    private String cpf;
    private Gender gender;

    public Specification<PhysicalPerson> toSpec() {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            name = FieldsFilter.validateFieldString(name);
            surname = FieldsFilter.validateFieldString(surname);
            email = FieldsFilter.validateFieldString(email);
            cpf = FieldsFilter.validateFieldString(cpf);

            Expression<String> nameField = builder.lower(root.get("name"));
            Predicate namePredicate = builder.like(nameField, "%" + name + "%");
            predicates.add(namePredicate);

            Expression<String> surnameField = builder.lower(root.get("surname"));
            Predicate surnamePredicate = builder.like(surnameField, "%" + surname + "%");
            Predicate andSurname = builder.and(surnamePredicate);
            predicates.add(andSurname);

            Expression<String> emailField = builder.lower(root.get("email"));
            Predicate emailPredicate = builder.like(emailField, "%" + email + "%");
            Predicate andEmail = builder.and(emailPredicate);
            predicates.add(andEmail);

            Expression<String> cpfField = builder.lower(root.get("cpf"));
            Predicate cpfPredicate = builder.like(cpfField, "%" + cpf + "%");
            Predicate andCpf = builder.and(cpfPredicate);
            predicates.add(andCpf);

            if (gender != null) {
                Path<Gender> genderField = root.get("gender");
                Predicate genderPredicate = builder.equal(genderField, gender);
                Predicate andGender = builder.and(genderPredicate);
                predicates.add(andGender);
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

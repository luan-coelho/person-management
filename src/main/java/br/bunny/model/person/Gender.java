package br.bunny.model.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum Gender {

    MALE(1, "Masculino"), FEMALE(2, "Feminino");

    private int id;
    private String label;

    public static Gender valueOf(int id) {
        for (Gender gender : Gender.values()) {
            if (gender.getId() == id)
                return gender;
        }
        return null;
    }
}

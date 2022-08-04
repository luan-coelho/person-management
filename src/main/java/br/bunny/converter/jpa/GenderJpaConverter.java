package br.bunny.converter.jpa;

import br.bunny.model.person.Gender;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class GenderJpaConverter implements AttributeConverter<Gender, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Gender gender) {
        return gender == null ? null : gender.getId();
    }

    @Override
    public Gender convertToEntityAttribute(Integer id) {
        return Gender.valueOf(id);
    }
}

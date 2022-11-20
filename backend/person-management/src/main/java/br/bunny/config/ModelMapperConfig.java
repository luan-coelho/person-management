package br.bunny.config;

import br.bunny.domain.model.DefaultEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@RequiredArgsConstructor
@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        modelMapper.addMappings(new PropertyMap<Object, DefaultEntity>() {
            @Override
            protected void configure() {
                skip(destination.getCreationDate());
                skip(destination.getChangeDate());
            }
        });

        return modelMapper;
    }
}

package br.bunny.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class Utils {

    private final ModelMapper mapper;

    public <T> Object convertObjectMapper(Object object, Class<T> destinationClass) {
        return mapper.map(object, destinationClass);
    }

    public static String removeAccentsWord(String word) {
        String normalizer = Normalizer.normalize(word, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalizer).replaceAll("");
    }

    public static Optional<String> convertObjectToJson(Object object) {
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(object);

            return Optional.of(json);
        } catch (Exception e) {
            log.error("Error converting Object to Json");
        }
        return Optional.empty();
    }


}

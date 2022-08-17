package br.bunny.util;

import br.bunny.domain.model.EmailRequest;
import br.bunny.domain.model.person.PhysicalPerson;
import br.bunny.exception.validation.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Service
public class EmailUtils {

    @Value("${email-microservice-url}")
    private String EMAIL_MICROSERVICE_BASE_URL;

    public void sendPasswordResetRequestEmail(PhysicalPerson person, String code) {
        EmailRequest emailRequest = EmailRequest.builder()
                .ownerRef(person.getName() + " " + person.getSurname())
                .emailTo(person.getEmail())
                .subject("Request to reset password")
                .text(String.format("Your code is %s <br> <br> Or click the button below to reset your password.", code))
                .build();

        Optional<String> emailRequestJson = Utils.convertObjectToJson(emailRequest);
        mountHttpClientPost(emailRequestJson.orElseThrow(() -> new BadRequestException("Error converting Object to Json")));
    }

    private void mountHttpClientPost(@NotBlank String requestBody) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(EMAIL_MICROSERVICE_BASE_URL.concat("/mse/send-password-reset-email"))).header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).join();
    }
}

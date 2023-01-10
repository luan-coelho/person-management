package br.bunny.service.password;

import br.bunny.domain.model.password.ChangePassword;
import br.bunny.domain.model.password.ForgotPassword;
import br.bunny.domain.model.person.PhysicalPerson;
import br.bunny.domain.repository.password.ForgotPasswordRepository;
import br.bunny.exception.validation.BadRequestException;
import br.bunny.service.person.PhysicalPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class ForgotPasswordService {

    private final ForgotPasswordRepository forgotPasswordRepository;
    private final PhysicalPersonService physicalPersonService;
    private final PasswordEncoder passwordEncoder;

    public boolean IsThereCodeRequest(String code) {
        return forgotPasswordRepository.existsByCode(code);
    }

    public void disableCode(String code) {
        Optional<ForgotPassword> codeRequest = forgotPasswordRepository.findByCode(code);
        codeRequest.orElseThrow(() -> new BadRequestException("Request not found by code"));
        codeRequest.get().setActive(false);
        forgotPasswordRepository.save(codeRequest.get());
    }

    public boolean codeAlreadyUsed(String code) {
        return forgotPasswordRepository.codeAlreadyUsed(code);
    }

    public void changePersonPassword(ChangePassword password) {
        if (IsThereCodeRequest(password.getCode())) {
            if (codeAlreadyUsed(password.getCode())) {
                if (ChangePassword.samePasswords(password.getNewPassword(), password.getConfirmPassword())) {
                    try {
                        Optional<ForgotPassword> forgotPasswordRequest = forgotPasswordRepository.findByCode(password.getCode());
                        forgotPasswordRequest.orElseThrow(() -> new BadRequestException("Request not found"));
                        PhysicalPerson person = physicalPersonService.findByEmail(forgotPasswordRequest.get().getPerson().getEmail());

                        person.setPassword(passwordEncoder.encode(password.getNewPassword()));

                        physicalPersonService.update(person.getId(), person);
                        disableCode(password.getCode());
                    } catch (Exception e) {
                        throw new BadRequestException("An unexpected error has occurred");
                    }
                } else throw new BadRequestException("Passwords don't match");

            } else throw new BadRequestException("The code provided is no longer valid");

        } else throw new BadRequestException("The given code does not exist");
    }
}

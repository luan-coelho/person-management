package br.bunny.service.password;

import br.bunny.exception.validation.BadRequestException;
import br.bunny.domain.model.password.ChangePassword;
import br.bunny.domain.model.password.ForgotPassword;
import br.bunny.domain.model.password.PasswordRecoveryRequest;
import br.bunny.domain.model.person.PhysicalPerson;
import br.bunny.domain.repository.ForgotPasswordRepository;
import br.bunny.service.person.PhysicalPersonService;
import br.bunny.util.EmailUtils;
import br.bunny.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ForgotPasswordService {

    private final ForgotPasswordRepository forgotPasswordRepository;
    private final PhysicalPersonService physicalPersonService;

    public boolean IsThereCodeRequest(String code) {
        return forgotPasswordRepository.existsByCode(code);
    }

    public boolean codeAlreadyUsed(String code) {
        return forgotPasswordRepository.codeAlreadyUsed(code);
    }

    public void passwordRecoveryRequest(PasswordRecoveryRequest recoveryRequest) {
        PhysicalPerson person = physicalPersonService.findPhysicalPersonByEmail(recoveryRequest.getEmail());

        String code = PasswordUtils.generatePasswordResetCode();

        EmailUtils.sendPasswordResetRequestEmail(person, code);

        forgotPasswordRepository.save(ForgotPassword.builder().code(PasswordUtils.generatePasswordResetCode()).person(person).dateTimeDeadline(LocalDateTime.now().plusDays(1)).active(true).build());
    }

    public void changePersonPassword(ChangePassword password) {
        if (IsThereCodeRequest(password.getCode())) {
            if (codeAlreadyUsed(password.getCode())) {
                if (ChangePassword.samePasswords(password.getNewPassword(), password.getConfirmPassword())) {
                    try {
                        Optional<ForgotPassword> forgotPasswordRequest = forgotPasswordRepository.findByCode(password.getCode());
                        forgotPasswordRequest.orElseThrow(() -> new BadRequestException("Request not found"));
                        PhysicalPerson person = physicalPersonService.findPhysicalPersonByEmail(forgotPasswordRequest.get().getPerson().getEmail());

                        person.setPassword(password.getNewPassword());
                        physicalPersonService.updatePhysicalPerson(person.getId(), person);
                    } catch (Exception e) {
                        throw new BadRequestException("An unexpected error has occurred");
                    }
                } else throw new BadRequestException("Passwords don't match");

            } else throw new BadRequestException("The given code has already been used");

        } else throw new BadRequestException("The given code does not exist");
    }
}

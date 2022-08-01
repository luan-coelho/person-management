package br.bunny.service.password;

import br.bunny.exception.validation.BadRequestException;
import br.bunny.model.password.ChangePassword;
import br.bunny.repository.ForgotPasswordRepository;
import br.bunny.service.person.PhysicalPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public boolean validateCode(String code) {
        return IsThereCodeRequest(code) && codeAlreadyUsed(code);
    }

    public void changeUserPassword(ChangePassword password) {
        if (!validateCode(password.getCode())) {
            throw new BadRequestException("Falha ao validar código.");
        }
    }
}

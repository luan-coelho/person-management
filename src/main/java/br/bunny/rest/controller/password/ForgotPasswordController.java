package br.bunny.rest.controller.password;

import br.bunny.exception.validation.BadRequestException;
import br.bunny.domain.model.password.ChangePassword;
import br.bunny.domain.model.password.ForgotPassword;
import br.bunny.domain.model.password.PasswordRecoveryRequest;
import br.bunny.service.password.ForgotPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/forgot-password")
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;

    @GetMapping
    public ResponseEntity<ForgotPassword> resetPassword(String code) {
        if (!forgotPasswordService.IsThereCodeRequest(code)) throw new BadRequestException("Code does not exist");
        if (!forgotPasswordService.codeAlreadyUsed(code)) throw new BadRequestException("Code already used");
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> passwordRecoveryRequest(@RequestBody PasswordRecoveryRequest recoveryRequest) {
        forgotPasswordService.passwordRecoveryRequest(recoveryRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePersonPassword(@RequestBody ChangePassword recoveryRequest) {
        forgotPasswordService.changePersonPassword(recoveryRequest);
        return ResponseEntity.ok().build();
    }
}

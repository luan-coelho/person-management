package br.bunny.controller.password;

import br.bunny.exception.NotFoundException;
import br.bunny.exception.validation.BadRequestException;
import br.bunny.model.password.ForgotPassword;
import br.bunny.service.password.ForgotPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping("/forgot-password")
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;

    @GetMapping
    public ResponseEntity<ForgotPassword> redefinePassword(@RequestParam String code) {
        if (!forgotPasswordService.IsThereCodeRequest(code))
            throw new NotFoundException("Código não existe.");

        if (!forgotPasswordService.codeAlreadyUsed(code))
            throw new BadRequestException("Código já usado.");
        return null;
    }

//    @PostMapping
//    public ResponseEntity<ForgotPassword> changePassword(@RequestBody ChangePassword password){
//        redefinePassword(password.getCode());
//        return ResponseEntity.ok(forgotPasswordService.changeUserPassword(password));
//    }

    @PostMapping
    public ResponseEntity<Void> passwordRecoveryRequest(@RequestBody HashMap<String, String> recoveryRequest) {
        return ResponseEntity.noContent().build();
    }
}

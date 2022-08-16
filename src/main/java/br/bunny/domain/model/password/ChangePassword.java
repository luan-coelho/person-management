package br.bunny.domain.model.password;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangePassword {

    private String code;
    private String newPassword;
    private String confirmPassword;

    public static boolean samePasswords(String newPassword, String confirmPassword) {
        return newPassword.equals(confirmPassword);
    }
}

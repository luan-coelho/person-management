package br.bunny.util;

import lombok.NoArgsConstructor;

import java.util.Random;

@NoArgsConstructor
public class PasswordUtils {

    public static String generatePasswordResetCode(){
        return Integer.toString(new Random().nextInt(9000)+1000);
    }
}

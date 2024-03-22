package com.adresse.model;

import java.util.regex.Pattern;

public class HelperMethod {
    public static boolean patternMatchesEmail(String emailAdress) {
        return Pattern.compile("^(.+)@(.+)$")
                .matcher(emailAdress)
                .matches();
    }

    public static boolean patternMatchesPassword(String password) {
        return Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{12,}$")
                .matcher(password)
                .matches();
    }
}

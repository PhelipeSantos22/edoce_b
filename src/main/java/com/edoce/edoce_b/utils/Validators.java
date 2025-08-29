package com.edoce.edoce_b.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Validators {
    default boolean isValidEmail(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern p = Pattern.compile(emailRegex);
        if (email == null){
            return false;
        }
        Matcher matcher = p.matcher(email);
        return matcher.matches();
    }

    default boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }

        if (password.length() <= 6) {
            return false;
        }

        boolean hasUpperCase = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
                break;
            }
        }
        if (!hasUpperCase) {
            return false;
        }

        boolean hasDigit = false;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigit = true;
                break;
            }
        }
        return hasDigit;
    }

    default boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        String name_regex = "^[a-zA-Z\\s]+$";
        return name.matches(name_regex);
    }
}

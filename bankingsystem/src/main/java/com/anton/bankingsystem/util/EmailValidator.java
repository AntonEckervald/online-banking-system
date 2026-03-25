package com.anton.bankingsystem.util;

import java.util.regex.Pattern;

public final class EmailValidator {
  private static final String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
  private static final Pattern p = Pattern.compile(emailRegex);

  private EmailValidator() {
  }

  public static boolean isValid(String email) {
    if (email == null || email.trim().isEmpty()) {
      return false;
    }

    return p.matcher(email).matches();
  }
}

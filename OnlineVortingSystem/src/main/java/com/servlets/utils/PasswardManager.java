package com.servlets.utils;

public class PasswardManager {
	// Method to encrypt the password using a simple Caesar cipher
    public String encryptPassword(String password, int shift) {
        StringBuilder encrypted = new StringBuilder();

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)||Character.isDigit(c)) {
                char shifted = (char) (c + shift);
                // Wrap around for lowercase letters
                if (Character.isLowerCase(c) && shifted > 'z') {
                    shifted -= 26;
                }
                // Wrap around for uppercase letters
                else if (Character.isUpperCase(c) && shifted > 'Z') {
                    shifted -= 26;
                }
                encrypted.append(shifted);
            } else {
                encrypted.append(c); // Keep non-alphabetic characters unchanged
            }
        }

        return encrypted.toString();
    }

    // Method to decrypt the password using a simple Caesar cipher
    public String decryptPassword(String encryptedPassword, int shift) {
        StringBuilder decrypted = new StringBuilder();

        for (char c : encryptedPassword.toCharArray()) {
            if (Character.isLetter(c)||Character.isDigit(c)) {
                char shifted = (char) (c - shift);
                // Wrap around for lowercase letters
                if (Character.isLowerCase(c) && shifted < 'a') {
                    shifted += 26;
                }
                // Wrap around for uppercase letters
                else if (Character.isUpperCase(c) && shifted < 'A') {
                    shifted += 26;
                }
                decrypted.append(shifted);
            } else {
                decrypted.append(c); // Keep non-alphabetic characters unchanged
            }
        }

        return decrypted.toString();
    }
}

package org.jala.university.utils;

public class Validations {

    public static boolean validateCPF(String cpf) {
        return cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");
    }
}

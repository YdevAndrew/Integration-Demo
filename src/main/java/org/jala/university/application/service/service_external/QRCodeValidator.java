package org.jala.university.application.service.service_external;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QRCodeValidator {

    // Valida o campo 'amount', que deve ser um número válido maior que zero
    public static boolean isValidAmount(double amount) {
        return amount > 0;
    }

    // Valida o campo 'cnpjReceiver', que deve ter exatamente 14 caracteres numéricos
    public static boolean isValidCNPJ(String cnpj) {
        // Regex para validar o CNPJ no formato xx.xxx.xxx/xxxx-xx
        String cnpjRegex = "^[0-9]{2}\\.[0-9]{3}\\.[0-9]{3}/[0-9]{4}-[0-9]{2}$";
        Pattern pattern = Pattern.compile(cnpjRegex);
        Matcher matcher = pattern.matcher(cnpj);
        return matcher.matches();

//        if (cnpj == null || cnpj.length() != 14) {
//            return false;
//        }
//
//        // Remove qualquer formatação de CNPJ (pontos, barra, traço)
//        cnpj = cnpj.replaceAll("[^\\d]", "");
//
//        // Verifica se o CNPJ contém exatamente 14 dígitos numéricos
//        return cnpj.matches("\\d{14}");
    }

    // Valida o nome do receptor, que deve ser uma string não vazia
    public static boolean isValidNameReceiver(String nameReceiver) {
        return nameReceiver != null && !nameReceiver.trim().isEmpty();
    }

    // Valida o campo 'accountReceiver', que deve ter de 8 a 11 números, com o último número separado por um traço
    public static boolean isValidAccount(String account) {
        // Regex para validar a conta no formato xxxxxxxx-x (8 a 11 números com um hífen no final)
        String accountRegex = "^[0-9]{7,8}-[0-9]{1}$";
        Pattern pattern = Pattern.compile(accountRegex);
        Matcher matcher = pattern.matcher(account);
        return matcher.matches();

//        if (account == null || account.length() < 8 || account.length() > 11) {
//            return false;
//        }
//
//        // Verifica o formato: 8-11 caracteres numéricos com um traço entre os últimos 2 dígitos
//        return account.matches("\\d{8,10}-\\d{1}");
    }

    // Valida o campo 'agencyReceiver', que deve ter exatamente 4 dígitos numéricos
    public static boolean isValidAgency(String agency) {
//        return agency != null && agency.matches("\\d{4}");
        // Verifica se a agência possui exatamente 4 dígitos numéricos
        String agencyRegex = "^[0-9]{4}$";
        Pattern pattern = Pattern.compile(agencyRegex);
        Matcher matcher = pattern.matcher(agency);
        return matcher.matches();
    }

    // Valida o campo 'expiredDate', que deve estar no formato dd/mm/yyyy e ser uma data válida
    public static boolean isValidExpiredDate(String expiredDate) {
        if (expiredDate == null) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);

        try {
            Date date = sdf.parse(expiredDate);
            return date != null;
        } catch (ParseException e) {
            return false;
        }
    }

    // Função para validar todos os dados de uma vez, retornando um objeto de erro (se houver)
    public static ValidationResult validateQRCodeData(double amount, String cnpjReceiver, String nameReceiver,
                                                      String agencyReceiver, String accountReceiver, String expiredDate) {
        if (!isValidAmount(amount)) {
            System.out.printf("Valor inválido. O valor deve ser maior que 0");
            return new ValidationResult(false, "Invalid amount.");
        }
        if (!isValidCNPJ(cnpjReceiver)) {
            System.out.printf("CNPJ inválido. Deve conter 14 números e estar formatado corretamente.");
            return new ValidationResult(false, "Invalid CNPJ.");
        }
        if (!isValidNameReceiver(nameReceiver)) {
            System.out.printf("Nome do destinatário inválido.");
            return new ValidationResult(false, "Invalid recipient name.");
        }
        if (!isValidAgency(agencyReceiver)) {
            System.out.printf("Agência inválida. Deve ter exatamente 4 números.");
            return new ValidationResult(false, "Invalid agency.");
        }
        if (!isValidAccount(accountReceiver)) {
            System.out.printf("Conta inválida. Deve ter de 8 a 11 números, com o último separado por '-'");
            return new ValidationResult(false, "Invalid account.");
        }
        if (!isValidExpiredDate(expiredDate)) {
            System.out.printf("Data de validade inválida. O formato deve ser dd/MM/yyyy.");
            return new ValidationResult(false, "Invalid expiration date.");
        }

        return new ValidationResult(true, "Valid QR Code.");
    }

    // Formata o CNPJ para o formato 'XX.XXX.XXX/XXXX-XX'
    public static String formatCNPJ(String cnpj) {
        // Remove qualquer formatação existente
        cnpj = cnpj.replaceAll("[^\\d]", "");

        if (cnpj.length() != 14) {
            return cnpj;  // Retorna o CNPJ sem formatação se ele for inválido
        }

        // Formata o CNPJ para o formato "XX.XXX.XXX/XXXX-XX"
        return cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "." + cnpj.substring(5, 8) + "/"
                + cnpj.substring(8, 12) + "-" + cnpj.substring(12, 14);
    }
}
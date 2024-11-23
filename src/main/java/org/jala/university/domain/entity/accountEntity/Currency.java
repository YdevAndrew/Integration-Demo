package org.jala.university.domain.entity.accountEntity;

import lombok.Getter;

@Getter
public enum Currency {
    USD("$"),
    EUR("€"),
    BRL("R$");

    private final String symbol;

    Currency(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return name();
    }

    public static Currency fromCode(String code) {
        for (Currency currency : Currency.values()) {
            if (currency.name().equalsIgnoreCase(code)) {
                return currency;
            }
        }
        throw new IllegalArgumentException("Código de moeda inválido: " + code);
    }
}

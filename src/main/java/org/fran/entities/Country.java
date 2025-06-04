package org.fran.entities;

public class Country {
    private String name;
    private String alpha2;
    private String currency;

    public Country() {
    }

    public Country(String name, String alpha2, String currency) {
        this.name = name;
        this.alpha2 = alpha2;
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlpha2(String alpha2) {
        this.alpha2 = alpha2;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAlpha2() {
        return alpha2;
    }
}

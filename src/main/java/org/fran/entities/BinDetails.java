package org.fran.entities;

public class BinDetails {
    private String scheme;
    private String type;
    private String brand;
    private boolean prepaid;
    private Country country;

    public BinDetails() {
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public boolean isPrepaid() {
        return prepaid;
    }

    public void setPrepaid(boolean prepaid) {
        this.prepaid = prepaid;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public BinDetails(String scheme, String type, String brand, boolean prepaid, Country country) {
        this.scheme = scheme;
        this.type = type;
        this.brand = brand;
        this.prepaid = prepaid;
        this.country = country;
    }

    public Country getCountry() {
        return country;
    }
}

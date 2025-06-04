package org.fran.dtos;

public class CountryCostItemDto {
    private String country;
    private int value;

    public CountryCostItemDto() {
    }

    public CountryCostItemDto(String country, int value) {
        this.country = country;
        this.value = value;
    }

    public String getCountry() {
        return country;
    }

    public int getValue() {
        return value;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

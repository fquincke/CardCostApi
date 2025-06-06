package org.fran.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BinDetailsTest {

    @Test
    void defaultConstructor_initializesFieldsAsNull() {
        BinDetails binDetails = new BinDetails();

        assertNull(binDetails.getScheme());
        assertNull(binDetails.getType());
        assertNull(binDetails.getBrand());
        assertFalse(binDetails.isPrepaid());
        assertNull(binDetails.getCountry());
    }

    @Test
    void parameterizedConstructor_setsAllFieldsCorrectly() {
        Country country = new Country();
        country.setAlpha2("US");

        BinDetails binDetails = new BinDetails(
                "visa", "debit", "Traditional", true, country
        );

        assertEquals("visa", binDetails.getScheme());
        assertEquals("debit", binDetails.getType());
        assertEquals("Traditional", binDetails.getBrand());
        assertTrue(binDetails.isPrepaid());
        assertEquals("US", binDetails.getCountry().getAlpha2());
    }

    @Test
    void settersAndGetters_workCorrectly() {
        BinDetails binDetails = new BinDetails();
        Country country = new Country();
        country.setAlpha2("DE");

        binDetails.setScheme("mastercard");
        binDetails.setType("credit");
        binDetails.setBrand("World Elite");
        binDetails.setPrepaid(false);
        binDetails.setCountry(country);

        assertEquals("mastercard", binDetails.getScheme());
        assertEquals("credit", binDetails.getType());
        assertEquals("World Elite", binDetails.getBrand());
        assertFalse(binDetails.isPrepaid());
        assertEquals("DE", binDetails.getCountry().getAlpha2());
    }

    @Test
    void isPrepaid_returnsCorrectBooleanValue() {
        BinDetails binDetails = new BinDetails();

        binDetails.setPrepaid(true);
        assertTrue(binDetails.isPrepaid());

        binDetails.setPrepaid(false);
        assertFalse(binDetails.isPrepaid());
    }

    @Test
    void setCountry_updatesCountryReference() {
        BinDetails binDetails = new BinDetails();
        Country country1 = new Country();
        country1.setAlpha2("US");

        Country country2 = new Country();
        country2.setAlpha2("FR");

        binDetails.setCountry(country1);
        assertEquals("US", binDetails.getCountry().getAlpha2());

        binDetails.setCountry(country2);

        assertEquals("FR", binDetails.getCountry().getAlpha2());
    }

    @Test
    void getCountry_returnsSameInstanceSet() {
        BinDetails binDetails = new BinDetails();
        Country country = new Country();
        country.setAlpha2("JP");

        binDetails.setCountry(country);

        assertSame(country, binDetails.getCountry());
    }

    @Test
    void fieldsCanBeUpdatedMultipleTimes() {
        BinDetails binDetails = new BinDetails();

        binDetails.setScheme("amex");
        binDetails.setType("credit");
        binDetails.setBrand("Platinum");
        binDetails.setPrepaid(true);

        assertEquals("amex", binDetails.getScheme());
        assertEquals("credit", binDetails.getType());
        assertEquals("Platinum", binDetails.getBrand());
        assertTrue(binDetails.isPrepaid());

        binDetails.setScheme("unionpay");
        binDetails.setType("debit");
        binDetails.setBrand("Standard");
        binDetails.setPrepaid(false);

        assertEquals("unionpay", binDetails.getScheme());
        assertEquals("debit", binDetails.getType());
        assertEquals("Standard", binDetails.getBrand());
        assertFalse(binDetails.isPrepaid());
    }

    @Test
    void handlesNullValuesCorrectly() {
        BinDetails binDetails = new BinDetails();

        binDetails.setScheme(null);
        binDetails.setType(null);
        binDetails.setBrand(null);
        binDetails.setCountry(null);

        assertNull(binDetails.getScheme());
        assertNull(binDetails.getType());
        assertNull(binDetails.getBrand());
        assertNull(binDetails.getCountry());
    }
}
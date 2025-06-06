package org.fran.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CountryTest {

    @Test
    void defaultConstructor_initializesFieldsAsNull() {
        Country country = new Country();

        assertNull(country.getName());
        assertNull(country.getAlpha2());
        assertNull(country.getCurrency());
    }

    @Test
    void parameterizedConstructor_setsAllFieldsCorrectly() {
        Country country = new Country("United States", "US", "USD");

        assertEquals("United States", country.getName());
        assertEquals("US", country.getAlpha2());
        assertEquals("USD", country.getCurrency());
    }

    @Test
    void settersAndGetters_workCorrectly() {
        Country country = new Country();

        country.setName("Germany");
        country.setAlpha2("DE");
        country.setCurrency("EUR");

        assertEquals("Germany", country.getName());
        assertEquals("DE", country.getAlpha2());
        assertEquals("EUR", country.getCurrency());
    }

    @Test
    void getAlpha2_returnsValueSetByConstructor() {
        Country country = new Country("France", "FR", "EUR");

        assertEquals("FR", country.getAlpha2());
    }

    @Test
    void getAlpha2_returnsValueSetBySetter() {
        Country country = new Country();
        country.setAlpha2("JP");

        assertEquals("JP", country.getAlpha2());
    }

    @Test
    void fieldsCanBeUpdatedMultipleTimes() {
        Country country = new Country("Initial", "XX", "AAA");

        country.setName("United Kingdom");
        country.setAlpha2("GB");
        country.setCurrency("GBP");

        assertEquals("United Kingdom", country.getName());
        assertEquals("GB", country.getAlpha2());
        assertEquals("GBP", country.getCurrency());

        country.setName("Canada");
        country.setAlpha2("CA");
        country.setCurrency("CAD");

        assertEquals("Canada", country.getName());
        assertEquals("CA", country.getAlpha2());
        assertEquals("CAD", country.getCurrency());
    }

    @Test
    void handlesNullValuesCorrectly() {
        Country country = new Country(null, null, null);

        country.setName(null);
        country.setAlpha2(null);
        country.setCurrency(null);

        assertNull(country.getName());
        assertNull(country.getAlpha2());
        assertNull(country.getCurrency());
    }

    @Test
    void alpha2SetterAndGetter_specialCases() {
        Country country = new Country();

        country.setAlpha2("");
        assertEquals("", country.getAlpha2());

        country.setAlpha2("us");
        assertEquals("us", country.getAlpha2());

        country.setAlpha2("12");
        assertEquals("12", country.getAlpha2());

        country.setAlpha2("A*");
        assertEquals("A*", country.getAlpha2());
    }

    @Test
    void currencySetterAndGetter_specialCases() {
        Country country = new Country();

        country.setCurrency("");
        assertEquals("", country.getCurrency());

        country.setCurrency("BTC");
        assertEquals("BTC", country.getCurrency());

        country.setCurrency("123");
        assertEquals("123", country.getCurrency());

        country.setCurrency("US_DOLLAR");
        assertEquals("US_DOLLAR", country.getCurrency());
    }

    @Test
    void nameSetterAndGetter_specialCases() {
        Country country = new Country();

        country.setName("");
        assertEquals("", country.getName());

        String longName = "The United Kingdom of Great Britain and Northern Ireland";
        country.setName(longName);
        assertEquals(longName, country.getName());

        country.setName("Côte d'Ivoire");
        assertEquals("Côte d'Ivoire", country.getName());

        country.setName("Country 123");
        assertEquals("Country 123", country.getName());
    }

    @Test
    void testEqualityAndHashCode() {
        Country country1 = new Country("USA", "US", "USD");
        Country country2 = new Country("USA", "US", "USD");
        Country country3 = new Country("Canada", "CA", "CAD");

        assertEquals(country1.getName(), country2.getName());
        assertEquals(country1.getAlpha2(), country2.getAlpha2());
        assertEquals(country1.getCurrency(), country2.getCurrency());

        assertNotEquals(country1.getName(), country3.getName());
        assertNotEquals(country1.getAlpha2(), country3.getAlpha2());
    }
}
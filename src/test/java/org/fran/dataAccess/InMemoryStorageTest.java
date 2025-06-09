package org.fran.dataAccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryStorageTest {

    private InMemoryStorage storage;

    @BeforeEach
    void setUp() {
        storage = new InMemoryStorage();
    }

    @Test
    void constructor_initializesDefaultValues() {
        Map<String, Integer> matrix = storage.getClearingCostMatrix();
        assertEquals(2, matrix.size());
        assertEquals(5, matrix.get("US"));
        assertEquals(10, matrix.get("GR"));
    }

    @Test
    void addOrUpdateCountrytoMatrix_newCountry_addsSuccessfully() {
        storage.addOrUpdateCountrytoMatrix("FR", 12);

        assertEquals(12, storage.getCostOfCard("FR"));
        assertTrue(storage.existsCountry("FR"));
    }

    @Test
    void addOrUpdateCountrytoMatrix_existingCountry_updatesValue() {
        storage.addOrUpdateCountrytoMatrix("FR", 12);

        storage.addOrUpdateCountrytoMatrix("FR", 15);

        assertEquals(15, storage.getCostOfCard("FR"));
    }

    @Test
    void getCostOfCard_existingCountry_returnsCorrectValue() {
        assertEquals(5, storage.getCostOfCard("US"));
        assertEquals(10, storage.getCostOfCard("GR"));

        storage.addOrUpdateCountrytoMatrix("DE", 8);
        assertEquals(8, storage.getCostOfCard("DE"));
    }

    @Test
    void getCostOfCard_nonExistingCountry_returnsDefaultValue() {
        assertEquals(15, storage.getCostOfCard("XX"));
    }

    @Test
    void existsCountry_existingCountry_returnsTrue() {
        assertTrue(storage.existsCountry("US"));
        assertTrue(storage.existsCountry("GR"));

        storage.addOrUpdateCountrytoMatrix("FR", 12);
        assertTrue(storage.existsCountry("FR"));
    }

    @Test
    void existsCountry_nonExistingCountry_returnsFalse() {
        assertFalse(storage.existsCountry("XX"));
    }

    @Test
    void getClearingCostMatrix_returnsAllEntries() {
        Map<String, Integer> matrix = storage.getClearingCostMatrix();

        assertEquals(2, matrix.size());
        assertEquals(5, matrix.get("US"));
        assertEquals(10, matrix.get("GR"));

        storage.addOrUpdateCountrytoMatrix("FR", 12);
        assertEquals(3, matrix.size());
        assertEquals(12, matrix.get("FR"));
    }

    @Test
    void deleteCountry_existingCountry_removesSuccessfully() {
        storage.deleteCountry("US");

        assertFalse(storage.existsCountry("US"));
        assertEquals(15, storage.getCostOfCard("US")); // Returns default after deletion
        assertEquals(1, storage.getClearingCostMatrix().size());
    }

    @Test
    void deleteCountry_nonExistingCountry_noChange() {
        storage.deleteCountry("XX");

        assertEquals(2, storage.getClearingCostMatrix().size());
    }

    @Test
    void caseSensitivity_countryCodesAreCaseSensitive() {
        storage.addOrUpdateCountrytoMatrix("fr", 12); // Lowercase
        storage.addOrUpdateCountrytoMatrix("FR", 15); // Uppercase

        assertTrue(storage.existsCountry("fr"));
        assertTrue(storage.existsCountry("FR"));
        assertEquals(12, storage.getCostOfCard("fr"));
        assertEquals(15, storage.getCostOfCard("FR"));
        assertEquals(4, storage.getClearingCostMatrix().size());
    }

    @Test
    void specialCharacters_countryCodesSupportSpecialChars() {
        storage.addOrUpdateCountrytoMatrix("U-S", 20);
        storage.addOrUpdateCountrytoMatrix("G*R", 25);

        assertEquals(20, storage.getCostOfCard("U-S"));
        assertEquals(25, storage.getCostOfCard("G*R"));
        assertTrue(storage.existsCountry("U-S"));
        assertTrue(storage.existsCountry("G*R"));
    }

    @Test
    void numericCountryCodes_supported() {
        storage.addOrUpdateCountrytoMatrix("123", 30);

        assertEquals(30, storage.getCostOfCard("123"));
        assertTrue(storage.existsCountry("123"));
    }

    @Test
    void negativeCosts_supported() {
        storage.addOrUpdateCountrytoMatrix("DE", -5);

        assertEquals(-5, storage.getCostOfCard("DE"));
    }

    @Test
    void zeroCost_supported() {
        storage.addOrUpdateCountrytoMatrix("CN", 0);

        assertEquals(0, storage.getCostOfCard("CN"));
    }

    @Test
    void largeCostValues_supported() {
        storage.addOrUpdateCountrytoMatrix("RU", Integer.MAX_VALUE);

        assertEquals(Integer.MAX_VALUE, storage.getCostOfCard("RU"));
    }
}
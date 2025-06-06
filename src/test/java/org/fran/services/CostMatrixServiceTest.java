package org.fran.services;

import org.fran.dataAccess.interfaces.IStorage;
import org.fran.dtos.CountryCostItemDto;
import org.fran.exeptions.CountryAlreadyExistsException;
import org.fran.exeptions.InvalidCountryException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CostMatrixServiceTest {

    @Mock
    private IStorage storage;

    @InjectMocks
    private CostMatrixService costMatrixService;

    private final String validCountry = "US";
    private final CountryCostItemDto validCostItem = new CountryCostItemDto(validCountry, 10);

    @Test
    void createCost_validInput_success() throws Exception {
        when(storage.existsCountry(validCountry)).thenReturn(false);

        costMatrixService.createCost(validCostItem);

        verify(storage).addOrUpdateCountrytoMatrix(validCountry, 10);
    }

    @Test
    void createCost_emptyCountry_throwsInvalidCountryException() {
        CountryCostItemDto invalidItem = new CountryCostItemDto("", 10);

        assertThrows(InvalidCountryException.class,
                () -> costMatrixService.createCost(invalidItem));
    }

    @Test
    void createCost_countryAlreadyExists_throwsCountryAlreadyExistsException() {
        when(storage.existsCountry(validCountry)).thenReturn(true);

        assertThrows(CountryAlreadyExistsException.class,
                () -> costMatrixService.createCost(validCostItem));
    }

    @Test
    void getCost_validCountry_returnsCostItem() throws Exception {
        when(storage.getCostOfCard(validCountry)).thenReturn(15);

        CountryCostItemDto result = costMatrixService.getCost(validCountry);

        assertEquals(validCountry, result.getCountry());
        assertEquals(15, result.getValue());
    }

    @Test
    void getCost_invalidCountry_throwsInvalidCountryException() {
        assertThrows(InvalidCountryException.class,
                () -> costMatrixService.getCost(""));
    }

    @Test
    void getCost_nonExistingCountry_returnsDefaultValue() throws Exception {
        when(storage.getCostOfCard("XX")).thenReturn(0);

        CountryCostItemDto result = costMatrixService.getCost("XX");

        assertEquals(0, result.getValue());
    }

    @Test
    void getAllCosts_returnsAllItems() {
        when(storage.getClearingCostMatrix()).thenReturn(
                Map.of("US", 10, "DE", 15, "FR", 12)
        );

        List<CountryCostItemDto> result = costMatrixService.getAllCosts();

        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(i -> "US".equals(i.getCountry()) && i.getValue() == 10));
        assertTrue(result.stream().anyMatch(i -> "DE".equals(i.getCountry()) && i.getValue() == 15));
        assertTrue(result.stream().anyMatch(i -> "FR".equals(i.getCountry()) && i.getValue() == 12));
    }

    @Test
    void getAllCosts_emptyStorage_returnsEmptyList() {
        when(storage.getClearingCostMatrix()).thenReturn(Map.of());

        List<CountryCostItemDto> result = costMatrixService.getAllCosts();

        assertTrue(result.isEmpty());
    }

    @Test
    void updateCost_validInput_success() throws Exception {
        when(storage.existsCountry(validCountry)).thenReturn(true);

        costMatrixService.updateCost(validCostItem);

        verify(storage).addOrUpdateCountrytoMatrix(validCountry, 10);
    }

    @Test
    void updateCost_nonExistingCountry_throwsInvalidCountryException() {
        when(storage.existsCountry("XX")).thenReturn(false);
        CountryCostItemDto invalidItem = new CountryCostItemDto("XX", 10);

        assertThrows(InvalidCountryException.class,
                () -> costMatrixService.updateCost(invalidItem));
    }

    @Test
    void updateCost_emptyCountry_throwsInvalidCountryException() {
        CountryCostItemDto invalidItem = new CountryCostItemDto("", 10);

        assertThrows(InvalidCountryException.class,
                () -> costMatrixService.updateCost(invalidItem));
    }

    @Test
    void deleteCost_validInput_success() throws Exception {
        when(storage.existsCountry(validCountry)).thenReturn(true);

        costMatrixService.deleteCost(validCountry);

        verify(storage).deleteCountry(validCountry);
    }

    @Test
    void deleteCost_nonExistingCountry_throwsInvalidCountryException() {
        when(storage.existsCountry("XX")).thenReturn(false);

        assertThrows(InvalidCountryException.class,
                () -> costMatrixService.deleteCost("XX"));
    }

    @Test
    void deleteCost_emptyCountry_throwsInvalidCountryException() {
        assertThrows(InvalidCountryException.class,
                () -> costMatrixService.deleteCost(""));
    }

    @Test
    void countryCodeIsAlwaysUppercase() throws Exception {
        // Test for create
        when(storage.existsCountry("US")).thenReturn(false);
        costMatrixService.createCost(new CountryCostItemDto("us", 10));
        verify(storage).addOrUpdateCountrytoMatrix("US", 10);
        reset(storage);

        // Test for get
        when(storage.getCostOfCard("US")).thenReturn(10);
        CountryCostItemDto result = costMatrixService.getCost("us");
        assertEquals("US", result.getCountry());
        reset(storage);

        // Test for update
        when(storage.existsCountry("US")).thenReturn(true);
        costMatrixService.updateCost(new CountryCostItemDto("us", 15));
        verify(storage).addOrUpdateCountrytoMatrix("US", 15);
        reset(storage);

        // Test for delete
        when(storage.existsCountry("US")).thenReturn(true);
        costMatrixService.deleteCost("us");
        verify(storage).deleteCountry("US");
    }
}
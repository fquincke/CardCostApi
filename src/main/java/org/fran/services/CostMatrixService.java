package org.fran.services;

import org.fran.dataAccess.interfaces.IStorage;
import org.fran.dtos.CountryCostItemDto;
import org.fran.exeptions.CountryAlreadyExistsException;
import org.fran.exeptions.InvalidCountryException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CostMatrixService {
    private final IStorage storage;

    public CostMatrixService(IStorage storage) {
        this.storage = storage;
    }

    public void createCost(CountryCostItemDto costRequestDto) throws CountryAlreadyExistsException, InvalidCountryException {
        String country = costRequestDto.getCountry();

        if (country == null || country.isBlank()) {
            throw new InvalidCountryException("Country can't be empty");
        }

        country = country.toUpperCase();
        if(storage.existsCountry(country)){
            throw new CountryAlreadyExistsException("Country already exists");
        }

        storage.addOrUpdateCountrytoMatrix(country, costRequestDto.getValue());
    }

    public CountryCostItemDto getCost(String country) throws InvalidCountryException {
        if (country == null || country.isBlank()) {
            throw new InvalidCountryException("Country can't be empty");
        }

        country = country.toUpperCase();
        int cost = storage.getCostOfCard(country);
        return new CountryCostItemDto(country, cost);
    }

    public List<CountryCostItemDto> getAllCosts() {
        List<CountryCostItemDto> result = new ArrayList<>();
        Map<String, Integer> matrix = storage.getClearingCostMatrix();

        for (Map.Entry<String, Integer> entry : matrix.entrySet()) {
            String country = entry.getKey();
            int cost = entry.getValue();

            CountryCostItemDto countryCostItemDto = new CountryCostItemDto(country, cost);
            result.add(countryCostItemDto);
        }

        return result;
    }

    public void updateCost(CountryCostItemDto costRequestDto) throws CountryAlreadyExistsException, InvalidCountryException {
        String country = costRequestDto.getCountry();

        if (country == null || country.isBlank()) {
            throw new InvalidCountryException("Country can't be empty");
        }

        country = country.toUpperCase();
        if(!storage.existsCountry(country)){
            throw new InvalidCountryException("Country does not exist");
        }

        storage.addOrUpdateCountrytoMatrix(country, costRequestDto.getValue());

    }

    public void deleteCost(String country) throws CountryAlreadyExistsException, InvalidCountryException {
        if (country == null || country.isBlank()) {
            throw new InvalidCountryException("Country can't be empty");
        }

        country = country.toUpperCase();
        if(!storage.existsCountry(country)){
            throw new InvalidCountryException("Country does not exist");
        }

        storage.deleteCountry(country);
    }
}

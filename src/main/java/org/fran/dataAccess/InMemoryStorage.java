package org.fran.dataAccess;

import org.fran.dataAccess.interfaces.IStorage;

import java.util.HashMap;
import java.util.Map;

public class InMemoryStorage implements IStorage {
    private Map<String, Integer> clearingCostMatrix;

    public InMemoryStorage(){
        clearingCostMatrix = new HashMap<>();

        clearingCostMatrix.put("US", 5);
        clearingCostMatrix.put("GR", 10);
    }

    public void addOrUpdateCountrytoMatrix(String country, int cost){
        clearingCostMatrix.put(country, cost);
    }

    public int getCostOfCard(String issuingCountry){
        return clearingCostMatrix.getOrDefault(issuingCountry, 15);
    }

    public boolean existsCountry(String issuingCountry){
        return clearingCostMatrix.containsKey(issuingCountry);
    }

    public Map<String, Integer> getClearingCostMatrix(){
        return clearingCostMatrix;
    }

    public void deleteCountry(String issuingCountry){
        clearingCostMatrix.remove(issuingCountry);
    }
}

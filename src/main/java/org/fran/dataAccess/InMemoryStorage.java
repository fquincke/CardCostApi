package org.fran.dataAccess;

import java.util.HashMap;
import java.util.Map;

public final class InMemoryStorage {
    private static InMemoryStorage instance;
    private Map<String, Integer> clearingCostMatrix;

     private InMemoryStorage(){
        clearingCostMatrix = new HashMap<>();

        clearingCostMatrix.put("US", 5);
        clearingCostMatrix.put("GR", 10);
    }
    public static InMemoryStorage getInstance(){
         if(instance == null){
             instance = new InMemoryStorage();
         }
         return instance;
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

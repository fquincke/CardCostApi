package org.fran.dataAccess.interfaces;

import java.util.Map;

public interface IStorage {

    public void addOrUpdateCountrytoMatrix(String country, int cost);

    public int getCostOfCard(String issuingCountry);

    public boolean existsCountry(String issuingCountry);

    public Map<String, Integer> getClearingCostMatrix();

    public void deleteCountry(String issuingCountry);

}

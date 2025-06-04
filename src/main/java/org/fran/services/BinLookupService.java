package org.fran.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import org.fran.dataAccess.InMemoryStorage;
import org.fran.dtos.LookupResponseDto;
import org.fran.entities.*;
import org.fran.exeptions.BinNotFoundExeption;
import org.fran.exeptions.InvalidCreditCardException;
import org.fran.exeptions.TooManyRequestsExeption;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BinLookupService {
    private InMemoryStorage storage;

    public BinLookupService(InMemoryStorage storage) {
        this.storage = storage;
    }

    public LookupResponseDto look(String cardNumber) throws TooManyRequestsExeption, IOException, InterruptedException {
        //validate input string
        if (cardNumber == null || cardNumber.trim().isEmpty() || cardNumber.length() < 8 || cardNumber.length() > 16) {
            throw new InvalidCreditCardException("Invalid credit card format. Credit card length must be between 8 and 16");
        }

        String bin = cardNumber.substring(0, 6);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://lookup.binlist.net/" + bin))
                .header("Accept-Version", "3") // Required header
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() == 200) {
            String responseBody = response.body();

            // Parse JSON Response
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            // Recommended additional configurations
            mapper.findAndRegisterModules();  // For Java 8 Date/Time support
            mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

            BinDetails binDetails = mapper.readValue(responseBody, BinDetails.class);

            var result = new LookupResponseDto();
            result.clearingCost = storage.getCostOfCard(binDetails.getCountry().getAlpha2());
            result.country = binDetails.getCountry().getAlpha2();

            return result;

        } else if (response.statusCode() == 400) {
            throw new BinNotFoundExeption("BIN not found");
        } else if (response.statusCode() == 429) {
            throw new TooManyRequestsExeption("The request limit to the API has been exceeded. Please wait.");
        } else {
            throw new IOException("Unexpected response code: " + response.statusCode());
        }

    }
}

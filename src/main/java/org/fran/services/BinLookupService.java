package org.fran.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import org.fran.dataAccess.interfaces.IStorage;
import org.fran.dtos.LookupResponseDto;
import org.fran.dtos.SimpleHttpResponse;
import org.fran.entities.BinDetails;
import org.fran.exeptions.BinNotFoundExeption;
import org.fran.exeptions.InvalidCreditCardException;
import org.fran.exeptions.TooManyRequestsExeption;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fran.services.interfaces.IHttpClientService;

public class BinLookupService {
    private final IStorage storage;
    private final IHttpClientService httpService;
    private String apiBaseUrl = "https://lookup.binlist.net/";

    // Production constructor
    public BinLookupService(IStorage storage) {
        this(storage, new HttpClientService());
    }

    // Test-friendly constructor
    public BinLookupService(IStorage storage,
                            IHttpClientService httpService) {
        this.storage = storage;
        this.httpService = httpService;
    }

    // For base URL customization in tests
    public void setApiBaseUrl(String baseUrl) {
        this.apiBaseUrl = baseUrl;
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.findAndRegisterModules();
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        return mapper;
    }

    public LookupResponseDto look(String cardNumber) throws Exception {
        validateCardNumber(cardNumber);
        String bin = cardNumber.substring(0, 6);

        // Use the httpService instead of direct HttpClient
        SimpleHttpResponse response = httpService.sendRequest(apiBaseUrl + bin);

        return handleResponse(response, bin);
    }

    private void validateCardNumber(String cardNumber) throws InvalidCreditCardException {
        if (cardNumber == null || cardNumber.trim().isEmpty() || cardNumber.length() < 8 || cardNumber.length() > 16) {
            throw new InvalidCreditCardException("Invalid credit card format. Credit card length must be between 8 and 16");
        }
    }

    private LookupResponseDto handleResponse(SimpleHttpResponse response, String bin) throws IOException {
        switch (response.getStatusCode()) {
            case 200:
                ObjectMapper objectMapper = createObjectMapper();
                BinDetails binDetails = objectMapper.readValue(response.getBody(), BinDetails.class);
                return createLookupResponse(binDetails);
            case 400:
                throw new BinNotFoundExeption("BIN not found");
            case 429:
                throw new TooManyRequestsExeption("The request limit to the API has been exceeded. Please wait.");
            default:
                throw new IOException("Unexpected response code: " + response.getStatusCode());
        }
    }

    private LookupResponseDto createLookupResponse(BinDetails binDetails) {
        LookupResponseDto result = new LookupResponseDto();
        result.clearingCost = storage.getCostOfCard(binDetails.getCountry().getAlpha2());
        result.country = binDetails.getCountry().getAlpha2();
        return result;
    }
}